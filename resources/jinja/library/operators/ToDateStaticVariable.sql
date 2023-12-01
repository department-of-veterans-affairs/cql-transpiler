{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ToDatePrint(environment, this, state, arguments) -%}
    {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}
{%- endmacro %}

{%- macro ToDateStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ToDate = namespace() %}
    {%- set environment.ToDate = ToDate %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ToDate) %}
    {%- set ToDate.defaultDataSQLFormat = environment.DataSQLFormatEnum.INHERITED %}
    {%- set ToDate.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set ToDate.defaultAccessType = environment.AccessTypeEnum.INHERITED %}
    {%- set ToDate.print = ToDatePrint %}
{%- endmacro %}