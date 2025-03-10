{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro OrPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} OR {{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }})
{%- endmacro %}

{%- macro OrStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Or = namespace() %}
    {%- set environment.Or = Or %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Or) %}
    {%- set Or.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Or.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Or.print = OrPrint %}
{%- endmacro %}