{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro QueryLetRefPrint(environment, this, state, arguments) -%}
    _let_{{ arguments['referencedName'] }}
{%- endmacro %}

{%- macro QueryLetRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set QueryLetRef = namespace() %}
    {%- set environment.QueryLetRef = QueryLetRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.QueryLetRef) %}
    {%- set QueryLetRef.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set QueryLetRef.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set QueryLetRef.defaultAccessType = environment.AccessTypeEnum.DOT_PROPERTY %}
    {%- set QueryLetRef.print = QueryLetRefPrint %}
{%- endmacro %}