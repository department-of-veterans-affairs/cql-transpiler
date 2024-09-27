{#- Prints the id matching the current context #}
{%- macro printIDFromContext(environment, context) -%}
    {{ {"Patient": "patientId"}[context] | default("cannotPrintIdFor_" ~ context) }}
{%- endmacro %}

{%- macro ContextHandlingFunctionsInit(environment) -%}
    {%- set environment.printIDFromContext = printIDFromContext %}
{%- endmacro %}
