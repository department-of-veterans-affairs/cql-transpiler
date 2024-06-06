
{% from "library/Init.sql" import init %}
{% from "generated/TestCQLLibrary.sql" import TestCQLLibraryPatient %}

{%- set environment = namespace() %}
{%- do init(environment) %}

{{ TestCQLLibraryPatient(environment, none) }}