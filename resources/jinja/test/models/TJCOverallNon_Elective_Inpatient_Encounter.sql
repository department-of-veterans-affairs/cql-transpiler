
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallNon_Elective_Inpatient_Encounter %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallNon_Elective_Inpatient_Encounter(environment, none) }}
