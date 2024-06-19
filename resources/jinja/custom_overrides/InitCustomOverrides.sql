{% from "library/Init.sql" import initEnvironment %}
{% from "custom_overrides/RetrieveCustomOverride.sql" import RetrieveCustomOverrideInit %}
{% from "custom_overrides/ValueSetRefCustomOverride.sql" import ValueSetRefCustomOverrideInit %}

{% macro initEnvironmentWithCustomOverrides(environment) %}
{%-     do initEnvironment(environment) %}
{%-     do RetrieveCustomOverrideInit(environment) %}
{%-     do ValueSetRefCustomOverrideInit(environment) %}
{%- endmacro %}