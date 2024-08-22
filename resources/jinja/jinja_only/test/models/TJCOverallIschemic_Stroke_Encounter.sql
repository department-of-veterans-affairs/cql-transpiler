{%- from "custom_overrides/InitCustomOverrides.sql" import initEnvironmentWithCustomOverrides %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallIschemic_Stroke_Encounter %}

{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallIschemic_Stroke_Encounter(environment, none) }}
