{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ValueSetRefPrint(environment, this, state, arguments) -%}
    {{ arguments['referenceTo']['value'] }}
{%- endmacro %}

{%- macro ValueSetRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ValueSetRef = namespace() %}
    {%- set environment.ValueSetRef = ValueSetRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ValueSetRef) %}
    {%- set ValueSetRef.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set ValueSetRef.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set ValueSetRef.print = ValueSetRefPrint %}
{%- endmacro %}