
{%- from "library/Init.sql" import initEnvironment %}
{%- from "generated/TJCOverall_7__1__000.sql" import TJCOverallTruncateTime %}

{%- set environment = namespace() %}
{%- do initEnvironment(environment) -%}

{{ TJCOverallTruncateTime(environment, none) }}
