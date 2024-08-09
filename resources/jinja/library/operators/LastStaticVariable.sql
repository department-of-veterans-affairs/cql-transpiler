{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro LastPrint(environment, this, state, arguments) -%}
    {%- if arguments['orderBy'] -%}
        /* unsupported argument in 'Last' orderBy:<{{ arguments['orderBy'] }}>*/
    {%- endif -%}
    last_value({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
{%- endmacro %}

{% macro LastStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Last = namespace() %}
    {%- set environment.Last = Last %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Last) %}
    {%- set Last.print = LastPrint %}
{%- endmacro %}