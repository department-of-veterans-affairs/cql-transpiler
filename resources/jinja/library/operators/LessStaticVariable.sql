{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro LessPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} < {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro LessStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Less = namespace() %}
    {%- set environment.Less = Less %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Less) %}
    {%- set Less.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Less.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Less.print = LessPrint %}
{%- endmacro %}