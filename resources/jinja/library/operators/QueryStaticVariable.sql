{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro queryStaticVariablePrintFundamentalSource(environment, this, state, source, returnClause) -%}
    {#- print the return clause #}
    {%- if returnClause == none %}SELECT * FROM {% else %}SELECT {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, returnClause) }} FROM {% endif %}
    {#- print the source clause. Parts of the source may previously have been encapsulated #}
    {%- set previousCoercionInstructions = state.coercionInstructions -%}
    ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, source) }})
    {%- set state.coercionInstructions = { environment.DataTypeEnum.ENCAPSULATED: environment.DataTypeEnum.TABLE } %}
    {%- set state.coercionInstructions = previousCoercionInstructions %}
{%- endmacro -%}

{%- macro queryStaticVariableWrapSourceInRelationshipClauses(environment, this, state, source, returnClause, relationshipClauses) -%}
    {#- TODO -#}
    /* Relationship clause functionality unsupported */
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
        {%- set previousCoercionInstructions = state.coercionInstructions %}
        {%- set state.coercionInstructions = {} %}
        {%- set previousAliasContext = state.aliasContext %}
        {%- set state.aliasContext = none %}
        {#- prefix with LET clauses, if any #}
        {%- if arguments['letClauseList']|length > 0 %}LET {{ environment.printOperatorsFromList(environment, state, arguments['letClauseList'], ", ") }} {% endif -%}
        {#- this function assumes responsiblity for handling AliasedQuerySource alias printing #}
        {%- if arguments['children'][0]['alias'] -%}
            {%- set state.aliasContext = arguments['children'][0]['alias'] %}
        {%- endif %}
        {#- print the core of a query -#}
        {{ queryStaticVariablePrintSource(environment, this, state, arguments['children'][0], arguments['returnClause'], state.aliasContext, arguments['relationshipClauseList']) }}
        {%- if arguments['where'] != none %} WHERE {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['where']) }}{% endif %}
        {%- if arguments['sortClause'] != none %} {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['sortClause']) }}{% endif %}
        {%- set state.aliasContext = previousAliasContext %}
        {%- set state.coercionInstructions = previousCoercionInstructions %}
    {%- endif %}
{%- endmacro %}

{%- macro QueryStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Query = namespace() %}
    {%- set environment.Query = Query %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Query) %}
    {%- set Query.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set Query.print = QueryPrint %}
{%- endmacro %}