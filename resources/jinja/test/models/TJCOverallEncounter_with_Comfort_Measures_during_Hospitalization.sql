
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization(environment, none) }}
