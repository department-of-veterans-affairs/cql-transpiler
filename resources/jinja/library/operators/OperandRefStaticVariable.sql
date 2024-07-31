{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro OperandRefPrint(environment, this, state, arguments) -%}
    {%- if state.functionArguments -%}
            {{ environment.OperatorHandler.print(environment, this, state, state.functionArguments[arguments['referenceTo']['name']]) }}
    {%- else -%}
            {{ environment.OperatorHandler.print(environment, this, state, arguments['referenceTo']) }}
    {%- endif %}
{%- endmacro %}

{%- macro OperandRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set OperandRef = namespace() %}
    {%- set environment.OperandRef = OperandRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.OperandRef) %}
    {%- set OperandRef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set OperandRef.print = OperandRefPrint %}
{%- endmacro %}