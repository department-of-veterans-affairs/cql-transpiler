{#- Prints the single value column name #}
{%- macro printSingleValueColumnName(environment) -%}
    _val
{%- endmacro %}

{#- Prints an empty table #}
{%- macro printEmptyTable(environment) -%}
    SELECT * FROM (SELECT 1 {{ printSingleValueColumnName(environment) }}) WHERE {{ printSingleValueColumnName(environment) }} = 0
{%- endmacro %}

{%- macro StandardsInit(environment) -%}
    {%- set environment.printSingleValueColumnName = printSingleValueColumnName %}
    {%- set environment.printEmptyTable = printEmptyTable %}
{%- endmacro %}
