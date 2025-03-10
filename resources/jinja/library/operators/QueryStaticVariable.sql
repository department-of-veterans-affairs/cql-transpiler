{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ConversionHandlingFunctions.sql" import ConversionHandlingFunctionsInit %}

{%- macro QueryGetAccessType(environment, this, state, arguments, carrier) %}
    {%- set carrier.value = environment.AccessTypeEnum.SELECT_FROM %}
    {%- if arguments['aliasedQuerySources']|length == 1 -%}
        {%- set childCarrier = namespace() %}
        {%- do arguments['aliasedQuerySources'][0]['operator'].getAccessType(environment, arguments['aliasedQuerySources'][0]['operator'], state, arguments['aliasedQuerySources'][0], childCarrier) %}
        {%- if childCarrier == environment.AccessTypeEnum.SCALAR_STRUCT %}
            {%- set carrier.value = environment.AccessTypeEnum.DOT_PROPERTY %}
        {%- endif %}
    {%- endif %}
{%- endmacro %}

{%- macro queryStaticVariableWrapSourceInRelationshipClauses(environment, this, state, localNamespace, aliasedQuerySource, relationshipClauseList) -%}
    {%- if (relationshipClauseList == none) or (relationshipClauseList|length == 0) %}
        {#- base case -#}
        {%- if localNamespace.sourceAlias %}
            {{ localNamespace.sourceAlias }} AS {{ state.aliasContext }}
        {%- else %}
            ({{ aliasedQuerySource['operator'].print(environment,  aliasedQuerySource['operator'], state,  aliasedQuerySource) }}) AS {{ state.aliasContext }}
        {%- endif %}
    {%- else %}
        {#- recursive step -#}
        {%- set currentRelationshipClause = relationshipClauseList[relationshipClauseList|length - 1] %}
        {%- set relationshipClauseListReducedByOne = relationshipClauseList[0:relationshipClauseList|length - 2] %}
        {#- base case -- one relationship clause -#}
        {%- set currentRelationshipClause = relationshipClauseList[relationshipClauseList|length - 1] %}
        {%- set relationshipClauseListReducedByOne = relationshipClauseList[0:relationshipClauseList|length - 2] %}
        (SELECT {{ aliasedQuerySource['alias'] }}.* FROM {{ queryStaticVariableWrapSourceInRelationshipClauses(environment, this, state, localNamespace, aliasedQuerySource, relationshipClauseListReducedByOne) }} LEFT JOIN {{ currentRelationshipClause['operator'].print(environment, currentRelationshipClause['operator'], state, currentRelationshipClause) }})
    {%- endif %}
{%- endmacro -%}

{%- macro QueryPrint(environment, this, state, arguments) -%}
    {%- if arguments['children'] -%}
        {{ environment.logError(environment, "TODO: Query -- support non-aliased sources") }}
    {%- elif arguments['aliasedQuerySources']|length == 1 -%}
        {#- sets the aliasContext to use in SingletonFrom operators #}
        {%- set previousAliasContext = state.aliasContext %}
        {%- set state.aliasContext = arguments['aliasedQuerySources'][0]['alias'] %}

        {%- if not arguments['returnClause'] and (arguments['aliasRefNodeList'] or arguments['letClauseList']) %}
            {#- exclude columns added for intermediate calculations -#}
            SELECT * EXCEPT (
                {%- set exceptList = [] -%}
                {%- for letClause in arguments['letClauseList'] -%}
                    {%- do exceptList.append("_let_"~letClause['referenceName'])  %}
                {%- endfor -%}
                {%- for aliasRef in arguments['aliasRefNodeList'] -%}
                    {%- do exceptList.append("_aliasRef_"~aliasRef['referencedName'])  %}
                {%- endfor -%}
                {#- #}{{ environment.printItemsFromList(environment, exceptList, ", ") }}{# -#}
            ) FROM {{ "(" }}
        {%- endif -%}

        {%- set localNamespace = namespace(sourceAlias = none) %}

        {%- if arguments['letClauseList'] or arguments['relationshipClauseList'] %}
            {#- include original source as CTE #}
            {%- if arguments['aliasRefNodeList'] -%}
                {#- -#}WITH _{{ state.aliasContext }} AS (SELECT _source.*, struct(*) AS _aliasRef_{{ state.aliasContext }} FROM ({{ arguments['aliasedQuerySources'][0]['operator'].print(environment,  arguments['aliasedQuerySources'][0]['operator'], state,  arguments['aliasedQuerySources'][0]) }}) AS _source)
            {%- else -%}
                {#- -#}WITH _{{ state.aliasContext }} AS ({{ arguments['aliasedQuerySources'][0]['operator'].print(environment,  arguments['aliasedQuerySources'][0]['operator'], state,  arguments['aliasedQuerySources'][0]) }})
            {%- endif %}
            {%- set localNamespace.sourceAlias = '_'~state.aliasContext %}

            {#- append let clauses as columns to original source #}
            {%- for letClause in arguments['letClauseList'] -%}
                {#- Let clause names have "cte" appended to them to make sure they're never accessed directly, except as the foundational source of a statement. Let clauses should be accessed as row references. -#}
                {#- -#}, _let_{{ letClause['referenceName']}}_cte AS (SELECT {{ state.aliasContext }}.*, {{ letClause['operator'].print(environment, letClause['operator'], state, letClause) }} FROM {{ localNamespace.sourceAlias }} AS {{ state.aliasContext }})
                {%- set localNamespace.sourceAlias = '_let_'~letClause['referenceName']~'_cte' %}
            {%- endfor %}

            {#- include relationship clause sources as CTEs #}
            {%- for source in arguments['relationshipClauseList'] -%}
                {#- -#}, {{ source['alias'] }} AS ({{ environment.coerceAndReformat(environment, state, source['child']['operator'], source['child'], none, environment.DataSQLFormatEnum.QUERY, false, namespace(), namespace()) }}) {# -#}
            {%- endfor %}
        {%- endif %}

        {#- print the return clause, if any #}
        {%- if arguments['returnClause'] -%}
            {#- #} SELECT ({{ arguments['returnClause']['operator'].print(environment, arguments['returnClause']['operator'], state, arguments['returnClause']) }}) FROM
        {%- else -%}
            {#- #} SELECT * FROM
        {%- endif -%}

        {#- Print main body of query #}
        {#- #} {{ queryStaticVariableWrapSourceInRelationshipClauses(environment, this, state, localNamespace, arguments['aliasedQuerySources'][0], arguments['relationshipClauseList']) }}

        {#- print any 'where' clauses #}
        {%- if arguments['where'] %}
            {#- #} WHERE {{ arguments['where']['operator'].print(environment,arguments['where']['operator'], state, arguments['where']) }}
        {%- endif %}

        {#- orders query, if necessary #}
        {%- if arguments['sortClause'] %}
            {#- #} {{ arguments['sortClause']['operator'].print(environment, arguments['sortClause']['operator'], state, arguments['sortClause']) }}
        {%- endif %}

        {#- conclude EXCEPT statement #}
        {%- if not arguments['returnClause'] and (arguments['aliasRefNodeList'] or arguments['letClauseList']) %}
            {{ ")" }}
        {%- endif %}

        {%- set state.aliasContext = previousAliasContext %}
    {%- else -%}
        {{ environment.logError(environment, "TODO: Query -- support queries with more or less than one aliased query source") }}
    {%- endif %}
{%- endmacro %}

{%- macro QueryStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do ConversionHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set Query = namespace() %}
    {%- set environment.Query = Query %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Query) %}
    {%- set Query.defaultDataSQLFormat = environment.DataSQLFormatEnum.QUERY %}
    {%- set Query.defaultDataState = environment.DataStateEnum.TABLE %}
    {%- set Query.defaultAccessType = environment.AccessTypeEnum.SELECT_FROM %}
    {%- set Query.getAccessType = QueryGetAccessType %}
    {%- set Query.print = QueryPrint %}
{%- endmacro %}