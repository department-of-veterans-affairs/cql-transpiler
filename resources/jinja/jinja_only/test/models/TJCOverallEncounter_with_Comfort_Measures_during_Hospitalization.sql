{%- from "custom_overrides/InitCustomOverrides.sql" import initEnvironmentWithCustomOverrides %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization %}

{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization(environment, none) }}
