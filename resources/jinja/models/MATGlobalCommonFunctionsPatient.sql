
{% from "library/Init.sql" import init %}
{% from "generated/MATGlobalCommonFunctions_7.0.000.sql" import MATGlobalCommonFunctionsPatient %}

{%- set environment = namespace() %}
{%- do init(environment) %}

{{ MATGlobalCommonFunctionsPatient(environment, none) }}