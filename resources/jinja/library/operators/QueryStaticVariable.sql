{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/TypeConversionFunctions.sql" import TypeConversionFunctionsInit %}

{%- macro queryStaticVariablePrintFundamentalSource(environment, this, state, source, returnClause) -%}
    {#- print the return clause #}
    {%- if returnClause -%}
        SELECT ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, returnClause) }}) FROM {# -#}
    {%- else -%}
        SELECT * FROM {# -#}
    {%- endif -%}
    ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, source) }})
{%- endmacro -%}

{%- macro queryStaticVariableWrapFundementalSourceInRelationshipClause(environment, this, state, source, returnClause, relationshipClause) -%}
    SELECT * FROM ({{ queryStaticVariablePrintFundamentalSource(environment, this, state, source, returnClause) }}) AS _source SEMI JOIN {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, relationshipClause) }}
{%- endmacro -%}

{%- macro queryStaticVariableWrapSourceInRelationshipClauses(environment, this, state, source, returnClause, relationshipClauses) -%}
    {%- if relationshipClauses|length > 1 -%}
        SELECT * FROM ({{ queryStaticVariableWrapSourceInRelationshipClauses(environment, this, state, source, returnClause, relationshipClauses[0:relationshipClauses|length - 2]) }}) AS _source SEMI JOIN {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, relationshipClause) }}
    {%- else -%}
        {{ queryStaticVariableWrapFundementalSourceInRelationshipClause(environment, this, state, source, returnClause, relationshipClauses[0]) }}
    {%- endif %}
{%- endmacro -%}

{%- macro queryStaticVariableWrapSourceInAlias(environment, this, state, source, returnClause, alias, relationshipClauses) -%}
    {%- if relationshipClauses == none or relationshipClauses|length == 0 -%}
        SELECT * FROM ({{ queryStaticVariablePrintFundamentalSource(environment, this, state, source, returnClause) }}) AS {{ alias }}
    {%- else -%}
        SELECT * FROM ({{ queryStaticVariableWrapSourceInRelationshipClauses(environment, this, state, source, returnClause, relationshipClauses) }}) AS {{ alias }}
    {%- endif %}
{%- endmacro -%}

{%- macro queryStaticVariablePrintSource(environment, this, state, source, returnClause, alias, relationshipClauses) -%}
    {#- print the return clause #}
    {%- if alias == none and relationshipClauses == none -%}
        {{ queryStaticVariablePrintFundamentalSource(environment, this, state, source, returnClause) }}
    {%- else -%}
        {%- if alias == none -%}
            {{ queryStaticVariableWrapSourceInWithClauses(environment, this, state, source, returnClause, relationshipClauses) }}
        {%- else -%}
            {{ queryStaticVariableWrapSourceInAlias(environment, this, state, source, returnClause, alias, relationshipClauses) }}
        {%- endif -%}
    {%- endif %}
{%- endmacro -%}

{%- macro QueryPrint(environment, this, state, arguments) -%}
    {%- if arguments['children']|length > 1 -%}
        /* multi-souce queries are not currently supported */
    {%- else -%}
        {%- set previousAliasContext = state.aliasContext %}
        {%- set state.aliasContext = none %}
        {#- prefix with LET clauses, if any #}
        {%- if arguments['letClauseList']|length > 0 -%}
            WITH {{ environment.printOperatorsFromList(environment, state, arguments['letClauseList'], ", ") }} {# -#}
        {%- endif -%}
        {#- this function assumes responsiblity for handling AliasedQuerySource alias printing #}
        {%- if arguments['children'][0]['alias'] -%}
            {%- set state.aliasContext = arguments['children'][0]['alias'] %}
        {%- endif %}
        {#- print the core of a query -#}
        {{ queryStaticVariablePrintSource(environment, this, state, arguments['children'][0], arguments['returnClause'], state.aliasContext, arguments['relationshipClauseList']) }}
        {%- if arguments['where'] != none %}
            {#- #} WHERE {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['where']) }}
        {%- endif %}
        {%- if arguments['sortClause'] != none %}
            {#- #} {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['sortClause']) }}
        {%- endif %}
        {%- set state.aliasContext = previousAliasContext %}
    {%- endif %}
{%- endmacro %}

{%- macro QueryStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do TypeConversionFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set Query = namespace() %}
    {%- set environment.Query = Query %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Query) %}
    {%- set Query.allowsSelectFromAccessTypeByDefault = true %}
    {%- set Query.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set Query.print = QueryPrint %}
{%- endmacro %}