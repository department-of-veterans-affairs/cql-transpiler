{%- from "custom_overrides/InitCustomOverrides.sql" import initEnvironmentWithCustomOverrides %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition %}

{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition(environment, none) }}
