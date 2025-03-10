{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro NotPrint(environment, this, state, arguments) -%}
    NOT {{ environment.coerceAndReformat(environment, state, arguments['child']['operator'], arguments['child'], none, none, true, namespace(), namespace()) }}
{%- endmacro %}

{%- macro NotStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Not = namespace() %}
    {%- set environment.Not = Not %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Not) %}
    {%- set Not.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Not.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Not.print = NotPrint %}
{%- endmacro %}