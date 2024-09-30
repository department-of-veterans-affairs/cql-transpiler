{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro QueryPrint(environment, this, state, arguments) -%}
    {%- set previousCoercionInstructions = state.coercionInstructions %}
    {%- set state.coercionInstructions = {} %}
    {%- set state.aliasContext = none %}
    {%- if arguments['letClauseList']|length > 0 %}LET {{ environment.printOperatorsFromList(environment, state, arguments['letClauseList'], ", ") }} {% endif -%}
    SELECT {% if arguments['returnClause'] == none %}*{% else %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['returnClause']) }}{%- endif %} FROM {% set state.coercionInstructions = { environment.DataTypeEnum.ENCAPSULATED: environment.DataTypeEnum.TABLE } %}{{ environment.printOperatorsFromList(environment, state, arguments['children'], ", ") }}{%- set state.coercionInstructions = {} %}
    {%- if arguments['where'] != none %} WHERE {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['where']) }}{% endif %}
    {%- if arguments['sortClause'] != none %} {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['sortClause']) }}{% endif %}
    {%- set state.aliasContext = none %}
    {%- set state.coercionInstructions = previousCoercionInstructions %}
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