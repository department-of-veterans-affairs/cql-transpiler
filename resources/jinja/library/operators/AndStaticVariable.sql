{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro AndPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} AND {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro AndStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set And = namespace() %}
    {%- set environment.And = And %}
    {%- do environment.OperatorClass.construct(environment, none, environment.And) %}
    {%- set And.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set And.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set And.print = AndPrint %}
{%- endmacro %}