{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ExistsPrint(environment, this, state, arguments) -%}
    EXISTS ({{ environment.OperatorHandler.print(environment, this, state, arguments['child'])}})
{%- endmacro %}

{% macro ExistsStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Exists = namespace() %}
    {%- set environment.Exists = Exists %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Exists) %}
    {%- set Exists.print = ExistsPrint %}
{%- endmacro %}