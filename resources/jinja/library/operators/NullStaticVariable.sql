{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro NullPrint(environment, this, state, arguments) -%}
    NULL
{%- endmacro %}

{%- macro NullStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Null = namespace() %}
    {%- set environment.Null = Null %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Null) %}
    {%- set Null.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Null.defaultDataState = environment.DataStateEnum.NULL %}
    {%- set Null.print = NullPrint %}
{%- endmacro %}