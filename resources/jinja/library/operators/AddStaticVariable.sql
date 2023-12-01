{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro AddPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} + {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro AddStaticVariableInit(environment) %}
    {# initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Add = namespace() %}
    {%- set environment.Add = Add %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Add) %}
    {%- set Add.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Add.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Add.print = AddPrint %}
{%- endmacro %}