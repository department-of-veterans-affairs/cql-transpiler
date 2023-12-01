{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro EqualPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} = {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro EqualStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Equal = namespace() %}
    {%- set environment.Equal = Equal %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Equal) %}
    {%- set Equal.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Equal.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Equal.print = EqualPrint %}
{%- endmacro %}