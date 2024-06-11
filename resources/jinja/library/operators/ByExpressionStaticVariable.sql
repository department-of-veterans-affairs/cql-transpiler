{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ByExpressionPrint(environment, this, state, arguments) -%}
{{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }} {{ arguments['direction'] }}
{%- endmacro %}

{% macro ByExpressionStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set ByExpression = namespace() %}
{%-     set environment.ByExpression = ByExpression %}
{%-     do environment.OperatorClass.construct(environment, none, environment.ByExpression) %}
{%-     set ByExpression.print = ByExpressionPrint %}
{%- endmacro %}