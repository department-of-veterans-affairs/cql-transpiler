{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro GreaterPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} > {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro GreaterStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Greater = namespace() %}
    {%- set environment.Greater = Greater %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Greater) %}
    {%- set Greater.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Greater.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Greater.print = GreaterPrint %}
{%- endmacro %}