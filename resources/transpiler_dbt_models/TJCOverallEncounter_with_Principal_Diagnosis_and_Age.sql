{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallEncounter_with_Principal_Diagnosis_and_Age(environment, none) }}
