{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ MATGlobalCommonFunctionsPatient(environment, none) }}