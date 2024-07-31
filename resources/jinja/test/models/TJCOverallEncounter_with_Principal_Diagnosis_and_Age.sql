
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallEncounter_with_Principal_Diagnosis_and_Age %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallEncounter_with_Principal_Diagnosis_and_Age(environment, none) }}
