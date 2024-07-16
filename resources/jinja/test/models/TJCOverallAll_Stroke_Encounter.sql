{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7.1.000.sql" import TJCOverallAll_Stroke_Encounter %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallAll_Stroke_Encounter(environment, none) }}
