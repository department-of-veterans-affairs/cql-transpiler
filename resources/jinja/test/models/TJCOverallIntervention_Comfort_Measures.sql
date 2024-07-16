
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7.1.000.sql" import TJCOverallIntervention_Comfort_Measures %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallIntervention_Comfort_Measures(environment, none) }}
