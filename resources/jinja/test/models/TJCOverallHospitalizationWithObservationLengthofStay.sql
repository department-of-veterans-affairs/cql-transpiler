
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallHospitalizationWithObservationLengthofStay %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallHospitalizationWithObservationLengthofStay(environment, none) }}
