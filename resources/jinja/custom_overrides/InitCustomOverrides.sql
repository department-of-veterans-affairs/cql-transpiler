{%- from "library/Init.sql" import initEnvironment %}
{%- from "library/jinja/custom_overrides/InValueSetCustomOverride.sql" import InValueSetCustomOverrideInit %}
{%- from "library/jinja/custom_overrides/ParameterRefCustomOverride.sql" import ParameterRefCustomOverrideInit %}
{%- from "library/jinja/custom_overrides/RetrieveCustomOverride.sql" import RetrieveCustomOverrideInit %}
{%- from "library/jinja/custom_overrides/ValueSetRefCustomOverride.sql" import ValueSetRefCustomOverrideInit %}

{%- macro initEnvironmentWithCustomOverrides(environment) %}
    {%- do initEnvironment(environment) %}
    {%- do InValueSetCustomOverrideInit(environment) %}
    {%- do ParameterRefCustomOverrideInit(environment) %}
    {%- do RetrieveCustomOverrideInit(environment) %}
    {%- do ValueSetRefCustomOverrideInit(environment) %}
{%- endmacro %}