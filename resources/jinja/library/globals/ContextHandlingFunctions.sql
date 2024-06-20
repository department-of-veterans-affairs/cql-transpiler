
{#- Prints the id matching the current context #}
{%- macro printIDFromContext(environment, context) -%}
    {{ context }}id
{%- endmacro %}

{%- macro ContextHandlingFunctionsInit(environment) -%}
    {%- set environment.printIDFromContext = printIDFromContext %}
{%- endmacro %}
