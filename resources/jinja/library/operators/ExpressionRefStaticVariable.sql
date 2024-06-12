{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ExpressionRefPrint(environment, this, state, arguments) -%}
{{ arguments['reference'](environment, state) }}
{%- endmacro %}

{% macro ExpressionRefStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set ExpressionRef = namespace() %}
{%-     set environment.ExpressionRef = ExpressionRef %}
{%-     do environment.OperatorClass.construct(environment, none, environment.ExpressionRef) %}
{%-     set ExpressionRef.defaultDataType = environment.DataTypeEnum.INHERITED %}
{%-     set ExpressionRef.print = ExpressionRefPrint %}
{%- endmacro %}