{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro LessOrEqualPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} <= {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro LessOrEqualStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set LessOrEqual = namespace() %}
    {%- set environment.LessOrEqual = LessOrEqual %}
    {%- do environment.OperatorClass.construct(environment, none, environment.LessOrEqual) %}
    {%- set LessOrEqual.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set LessOrEqual.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set LessOrEqual.print = LessOrEqualPrint %}
{%- endmacro %}