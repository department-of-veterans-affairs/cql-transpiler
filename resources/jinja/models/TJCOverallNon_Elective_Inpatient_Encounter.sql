
{% from "library/Init.sql" import init %}
{% from "generated/TJCOverall_7.1.000.sql" import TJCOverallNon_Elective_Inpatient_Encounter %}

{%- set environment = namespace() %}
{%- do init(environment) %}

{{ TJCOverallNon_Elective_Inpatient_Encounter(environment, none) }}
