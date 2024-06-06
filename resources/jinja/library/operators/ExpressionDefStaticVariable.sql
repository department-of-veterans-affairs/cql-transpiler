{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro printExpressionDef(environment, this, state, arguments) %}
{%-     set previousContext = state.context %}
{%-     if previousContext == none and arguments['context'] != none %}
{%-         set state.context = arguments['context'] %}
{%-     endif %}
{{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }}
{%-     set state.context = previousContext %}
{%- endmacro %}

{% macro ExpressionDefStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set ExpressionDef = namespace() %}
{%-     set environment.ExpressionDef = ExpressionDef %}
{%-     do environment.OperatorClass.construct(environment, none, environment.ExpressionDef) %}
{%-     set ExpressionDef.defaultDataType = environment.DataTypeEnum.INHERITED %}
{%-     set ExpressionDef.print = printExpressionDef %}
{%- endmacro %}