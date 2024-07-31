{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallAll_Stroke_Encounter %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallAll_Stroke_Encounter(environment, none) }}
