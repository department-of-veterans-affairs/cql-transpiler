{%- from "library/globals/OperatorHandlerClass.sql" import OperatorHandlerClassInit %}

{%- macro OperatorHandlerStaticVariableInit(environment) -%}
    {#- initialize prerequisites #}
    {%-  do OperatorHandlerClassInit(environment) %}
    {#- initialize member variables #}
    {%- set environment.OperatorHandler = namespace() %}
    {%- do environment.OperatorHandlerClass.construct(environment, environment.OperatorHandler) %}
{%- endmacro %}