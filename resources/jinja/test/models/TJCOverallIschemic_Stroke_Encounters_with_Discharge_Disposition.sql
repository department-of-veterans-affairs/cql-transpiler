
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition(environment, none) }}
