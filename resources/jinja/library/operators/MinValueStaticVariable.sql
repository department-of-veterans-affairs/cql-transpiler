{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro MinValuePrint(environment, this, state, arguments) -%}
    {{ environment.logError(environment, "TODO: MinValue") }}
{%- endmacro %}

{%- macro MinValueStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set MinValue = namespace() %}
    {%- set environment.MinValue = MinValue %}
    {%- do environment.OperatorClass.construct(environment, none, environment.MinValue) %}
    {%- set MinValue.print = MinValuePrint %}
{%- endmacro %}