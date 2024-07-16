
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7.1.000.sql" import TJCOverallHospitalizationWithObservationLengthofStay %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallHospitalizationWithObservationLengthofStay(environment, none) }}
