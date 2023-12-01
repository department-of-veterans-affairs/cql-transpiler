{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro SubtractPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} - {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro SubtractStaticVariableInit(environment) %}
    {# initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Subtract = namespace() %}
    {%- set environment.Subtract = Subtract %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Subtract) %}
    {%- set Subtract.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Subtract.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Subtract.print = SubtractPrint %}
{%- endmacro %}