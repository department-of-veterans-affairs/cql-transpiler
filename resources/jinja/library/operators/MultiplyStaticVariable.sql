{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro MultiplyPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} * {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro MultiplyStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Multiply = namespace() %}
    {%- set environment.Multiply = Multiply %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Multiply) %}
    {%- set Multiply.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Multiply.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Multiply.print = MultiplyPrint %}
{%- endmacro %}