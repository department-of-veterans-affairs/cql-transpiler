{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro DividePrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} / {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro DivideStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Divide = namespace() %}
    {%- set environment.Divide = Divide %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Divide) %}
    {%- set Divide.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW %}
    {%- set Divide.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Divide.print = DividePrint %}
{%- endmacro %}