{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro UnionPrint(environment, this, state, arguments) -%}
    {%- set previousCoercionInstructions = state.coercionInstructions %}
    {%- set state.coercionInstructions = { environment.DataTypeEnum.ENCAPSULATED: environment.DataTypeEnum.TABLE } -%}
    {{ environment.printOperatorsFromList(environment, state, arguments['children'], ' UNION ') }}
    {%- set state.coercionInstructions = previousCoercionInstructions %}
{%- endmacro %}

{%- macro UnionStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Union = namespace() %}
    {%- set environment.Union = Union %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Union) %}
    {%- set Union.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set Union.print = UnionPrint %}
{%- endmacro %}