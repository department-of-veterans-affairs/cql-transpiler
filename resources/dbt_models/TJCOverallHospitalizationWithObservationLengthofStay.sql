{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallHospitalizationWithObservationLengthofStay(environment, none) }}
