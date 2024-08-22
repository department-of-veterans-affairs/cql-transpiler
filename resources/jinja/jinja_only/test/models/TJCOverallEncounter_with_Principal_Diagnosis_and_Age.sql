{%- from "custom_overrides/InitCustomOverrides.sql" import initEnvironmentWithCustomOverrides %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallEncounter_with_Principal_Diagnosis_and_Age %}

{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallEncounter_with_Principal_Diagnosis_and_Age(environment, none) }}
