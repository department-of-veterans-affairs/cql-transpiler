{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallAll_Stroke_Encounter(environment, none) }}
