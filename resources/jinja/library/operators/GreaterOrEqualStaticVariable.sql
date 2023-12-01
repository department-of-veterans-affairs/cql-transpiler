{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro GreaterOrEqualPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} >= {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro GreaterOrEqualStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set GreaterOrEqual = namespace() %}
    {%- set environment.GreaterOrEqual = GreaterOrEqual %}
    {%- do environment.OperatorClass.construct(environment, none, environment.GreaterOrEqual) %}
    {%- set GreaterOrEqual.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set GreaterOrEqual.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set GreaterOrEqual.print = GreaterOrEqualPrint %}
{%- endmacro %}