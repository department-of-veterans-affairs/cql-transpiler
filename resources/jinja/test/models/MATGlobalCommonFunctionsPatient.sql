
{% from "library/Init.sql" import initEnvironment %}
{% from "generated/MATGlobalCommonFunctions_7__0__000.sql" import MATGlobalCommonFunctionsPatient %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) %}

{{ MATGlobalCommonFunctionsPatient(environment, none) }}