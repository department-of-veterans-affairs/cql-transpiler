{#
    Environment prerequisites:
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro RetrievePrint(environment, this, state, arguments) -%}
/* Retrieve with state: <{{ state }}>, arguments: <{{ arguments }}> */
{%- endmacro %}

{% macro RetrieveStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set Retrieve = namespace() %}
{%-     set Retrieve.defaultDataType = environment.DataTypeEnum.TABLE %}
{%-     do environment.OperatorClass.construct(environment, none, Retrieve) %}
{%-     set Retrieve.print = RetrievePrint %}
{%-     set environment.Retrieve = Retrieve %}
{%- endmacro %}