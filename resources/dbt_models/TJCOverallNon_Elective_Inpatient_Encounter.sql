{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallNon_Elective_Inpatient_Encounter(environment, none) }}
