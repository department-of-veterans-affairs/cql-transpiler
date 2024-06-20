{%- from "library/Init.sql" import initEnvironment %}
{%- from "custom_overrides/RetrieveCustomOverride.sql" import RetrieveCustomOverrideInit %}

{%- macro initEnvironmentWithCustomOverrides(environment) %}
{%-     do initEnvironment(environment) %}
{%-     do RetrieveCustomOverrideInit(environment) %}
{%- endmacro %}