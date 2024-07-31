
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallIntervention_Comfort_Measures %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallIntervention_Comfort_Measures(environment, none) }}
