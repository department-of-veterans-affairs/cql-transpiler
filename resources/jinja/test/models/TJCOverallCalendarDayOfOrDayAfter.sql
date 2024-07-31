
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallCalendarDayOfOrDayAfter %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallCalendarDayOfOrDayAfter(environment, none) }}
