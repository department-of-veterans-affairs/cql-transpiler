{%- from "custom_overrides/InitCustomOverrides.sql" import initEnvironmentWithCustomOverrides %}
{%- from "generated/MATGlobalCommonFunctions_7__0__000.sql" import MATGlobalCommonFunctionsPatient %}

{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ MATGlobalCommonFunctionsPatient(environment, none) }}