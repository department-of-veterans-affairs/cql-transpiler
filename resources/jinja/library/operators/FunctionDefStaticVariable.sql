{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro FunctionDefPrint(environment, this, state, arguments) -%}
{#     arguments['typeSpecifier'] is unused #}
{%-     set previousOperandsMatchedToFunctionArguments = state.operandsMatchedToFunctionArguments %}
{%-     set state.operandsMatchedToFunctionArguments = {} %}
{%-     for item in state.functionArguments %}
{%-         do state.operandsMatchedToFunctionArguments.update({ arguments.operators[loop.index - 1].name: item }) %}
{%-     endfor %}
{{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }}
{%-     set state.operandsMatchedToFunctionArguments = previousOperandsMatchedToFunctionArguments %}
{%- endmacro %}

{% macro FunctionDefStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set FunctionDef = namespace() %}
{%-     set environment.FunctionDef = FunctionDef %}
{%-     do environment.OperatorClass.construct(environment, none, environment.FunctionDef) %}
{%-     set Retrieve.defaultDataType = environment.DataTypeEnum.INHERITED %}
{%-     set FunctionDef.print = FunctionDefPrint %}
{%- endmacro %}