{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ParameterRefPrint(environment, this, state, arguments) -%}
    @{{ arguments['name'] }}
{%- endmacro %}

{%- macro ParameterRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set ParameterRef = namespace() %}
    {%- set environment.ParameterRef = ParameterRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ParameterRef) %}
    {%- set ParameterRef.print = ParameterRefPrint %}
{%- endmacro %}