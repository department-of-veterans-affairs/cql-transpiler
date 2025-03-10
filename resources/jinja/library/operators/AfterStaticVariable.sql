{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro AfterPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} > {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro AfterStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set After = namespace() %}
    {%- set environment.After = After %}
    {%- do environment.OperatorClass.construct(environment, none, environment.After) %}
    {%- set After.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set After.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set After.print = AfterPrint %}
{%- endmacro %}