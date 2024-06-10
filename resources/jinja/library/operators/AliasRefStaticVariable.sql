{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro AliasRefPrint(environment, this, state, arguments) -%}
{{ arguments['name'] }}
{%- endmacro %}

{% macro AliasRefStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set AliasRef = namespace() %}
{%-     set environment.AliasRef = AliasRef %}
{%-     do environment.OperatorClass.construct(environment, none, environment.AliasRef) %}
{%-     set AliasRef.print = AliasRefPrint %}
{%- endmacro %}