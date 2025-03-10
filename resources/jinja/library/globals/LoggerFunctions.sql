{#- Prints debug messages #}
{%- macro logDebug(environment, text) -%}
    {%- if environment.debug %}/* debug -- {{ text }} */{% endif -%}
{%- endmacro %}

{#- Prints warning messages #}
{%- macro logWarning(environment, text) -%}
    {%- if environment.warning %}/* warning -- {{ text }} */{% endif -%}
{%- endmacro %}

{#- Prints error messages #}
{%- macro logError(environment, text) -%}
    {%- if environment.error %}/* error -- {{ text }} */{% endif -%}
{%- endmacro %}

{%- macro LoggerFunctionsInit(environment) -%}
    {%- set environment.debug = true %}
    {%- set environment.warning = true %}
    {%- set environment.error = true %}
    {%- set environment.logDebug = logDebug %}
    {%- set environment.logWarning = logWarning %}
    {%- set environment.logError = logError %}
{%- endmacro %}
