{#
    Loads operators into environment
#}
{% from "custom_overrides/RetrieveCustomOverride.sql" import RetrieveCustomOverrideInit %}

{% macro init(environment) %}
{%-     do RetrieveCustomOverrideInit(environment) %}
{%- endmacro %}