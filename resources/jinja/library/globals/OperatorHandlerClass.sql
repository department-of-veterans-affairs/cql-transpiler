{%- from "library/globals/StateClass.sql" import StateClassInit %}

{%- macro OperatorHandlerPrint(environment, this, state, arguments) %}
    {%- if state == none %}
        {%- set state = namespace() %}
        {%- do environment.StateClass.construct(environment, state) %}
    {%- endif -%}
    {{ arguments['operator'].print(environment, arguments['operator'], state, arguments) }}
{%- endmacro %}

{%- macro OperatorHandlerConstruct(environment, operatorHandlerNamespace) %}
    {%- set operatorHandlerNamespace.print = OperatorHandlerPrint %}
{%- endmacro %}

{%- macro OperatorHandlerClassInit(environment) -%}
    {#-  initialize prerequisites #}
    {%- do StateClassInit(environment) %}
    {#-  initialize OperatorHandlerClass #}
    {%- set OperatorHandlerClass = namespace() %}
    {%- set OperatorHandlerClass.construct = OperatorHandlerConstruct %}
    {%- set environment.OperatorHandlerClass = OperatorHandlerClass %}
{%- endmacro %}