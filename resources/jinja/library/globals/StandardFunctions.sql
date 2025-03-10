{#- Prints the single value column name #}
{%- macro printSingleValueColumnName(environment) -%}
    _val
{%- endmacro %}

{#- Prints an empty table #}
{%- macro printEmptyTable(environment) -%}
    SELECT * FROM (SELECT 1 {{ printSingleValueColumnName(environment) }}) WHERE {{ printSingleValueColumnName(environment) }} = 0
{%- endmacro %}

{%- macro wrapAsSingleValueTable(environment, toWrap) -%}
    SELECT {{ toWrap }} {{ environment.printSingleValueColumnName(environment) }}
{%- endmacro %}

{%- macro StandardFunctionsInit(environment) -%}
    {%- set environment.printSingleValueColumnName = printSingleValueColumnName %}
    {%- set environment.printEmptyTable = printEmptyTable %}
    {%- set environment.wrapAsSingleValueTable = wrapAsSingleValueTable %}
{%- endmacro %}
