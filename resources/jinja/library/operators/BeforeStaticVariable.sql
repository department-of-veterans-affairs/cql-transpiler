{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro BeforePrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} < {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro BeforeStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Before = namespace() %}
    {%- set environment.Before = Before %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Before) %}
    {%- set Before.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Before.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Before.print = BeforePrint %}
{%- endmacro %}