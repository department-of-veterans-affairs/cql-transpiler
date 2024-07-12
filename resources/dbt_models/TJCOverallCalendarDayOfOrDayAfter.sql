{%- set environment = namespace() %}
{%- do initEnvironmentWithCustomOverrides(environment) -%}

{{ TJCOverallCalendarDayOfOrDayAfter(environment, none) }}
