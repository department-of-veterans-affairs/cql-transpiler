{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro FunctionRefPrint(environment, this, state, arguments) -%}
    {% set functionArguments = namespace() %}
    {% set previousFunctionArguments = state.functionArguments %}
    {% for key, value in previousFunctionArguments %}
        {% set functionArguments[key] = value %}
    {% endfor %}
    {% set arguments.functionArguments = functionArguments %}
    {% for value in arguments['functionOperators']}
        {% set functionArguments[value['name']] = value %}
    {% endfor %}
    {{ arguments['reference'](environment, state) }}
    {% set arguments.functionArguments = previousFunctionArguments %}
{%- endmacro %}

{%- macro FunctionRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set FunctionRef = namespace() %}
    {%- set environment.FunctionRef = FunctionRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.FunctionRef) %}
    {%- set FunctionRef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set FunctionRef.print = FunctionRefPrint %}
{%- endmacro %}