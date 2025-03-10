{%- from "library/Init.sql" import initEnvironment %}
{%- from "custom_overrides/InValueSetCustomOverride.sql" import InValueSetCustomOverrideInit %}
{%- from "custom_overrides/ParameterRefCustomOverride.sql" import ParameterRefCustomOverrideInit %}
{%- from "custom_overrides/ValueSetRefCustomOverride.sql" import ValueSetRefCustomOverrideInit %}

{%- macro initEnvironmentWithCustomOverrides(environment) %}
    {%- do initEnvironment(environment) %}
    {%- do InValueSetCustomOverrideInit(environment) %}
    {%- do ParameterRefCustomOverrideInit(environment) %}
    {%- do ValueSetRefCustomOverrideInit(environment) %}
{%- endmacro %}