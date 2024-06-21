{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ListPrint(environment, this, state, arguments) -%}
    SELECT collect_list({{ environment.printSingleValueColumnName(environment) }}) AS {{ environment.printSingleValueColumnName(environment) }} FROM (
    {%- if arguments['children']|length == 0 -%}
        {{ environment.printEmptyTable(environment) }}
    {%- else %}
        {%- set previousCoercionInstructions = state.coercionInstructions %}
        {%- set state.coercionInstructions = { environment.DataTypeEnum.SIMPLE: environment.DataTypeEnum.ENCAPSULATED } -%}
        {{ environment.printOperatorsFromList(environment, state, arguments['children'], ' UNION ') }}
        {%- set state.coercionInstructions = previousCoercionInstructions %}
    {%- endif %})
{%- endmacro %}

{%- macro ListStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set List = namespace() %}
    {%- set environment.List = List %}
    {%- do environment.OperatorClass.construct(environment, none, environment.List) %}
    {%- set List.defaultDataType = environment.DataTypeEnum.ENCAPSULATED %}
    {%- set List.print = ListPrint %}
{%- endmacro %}