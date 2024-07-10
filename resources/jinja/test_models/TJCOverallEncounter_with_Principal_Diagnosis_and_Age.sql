
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7.1.000.sql" import TJCOverallEncounter_with_Principal_Diagnosis_and_Age %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallEncounter_with_Principal_Diagnosis_and_Age(environment, none) }}
