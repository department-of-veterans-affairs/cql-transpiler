{%- from "custom_overrides/InitCustomOverrides.sql" import initEnvironmentWithCustomOverrides %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallIntervention_Comfort_Measures %}

{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallIntervention_Comfort_Measures(environment, none) }}
