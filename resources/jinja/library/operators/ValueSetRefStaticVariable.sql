{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ValueSetRefPrint(environment, this, state, arguments) -%}
{{ arguments['reference'] }}
{%- endmacro %}

{% macro ValueSetRefStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set ValueSetRef = namespace() %}
{%-     set environment.ValueSetRef = ValueSetRef %}
{%-     do environment.OperatorClass.construct(environment, none, environment.ValueSetRef) %}
{%-     set ValueSetRef.print = ValueSetRefPrint %}
{%- endmacro %}