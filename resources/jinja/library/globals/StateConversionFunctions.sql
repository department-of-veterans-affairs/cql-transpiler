{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}
{%- from "library/globals/ContextHandlingFunctions.sql" import ContextHandlingFunctionsInit %}
{%- from "library/globals/ListPrintingFunctions.sql" import ListPrintingFunctionsInit %}

{#- Wraps SQL statement in a collect block #}
{%- macro collect(environment, context, toCollect) -%}
    SELECT collect_list(struct(*)) AS {{ environment.printSingleValueColumnName(environment) }} FROM ({{ toCollect }})
{%- endmacro %}

{#- Wraps SQL statement in a decollect block #}
{%- macro decollect(environment, context, toDecollect) -%}
    SELECT col.* FROM (explode({{ toDecollect }}))
{%- endmacro %}

{%- macro StateConversionFunctionsInit(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardFunctionsInit(environment) %}
    {%- do ContextHandlingFunctionsInit(environment) %}
    {%- do ListPrintingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set environment.collect = collect %}
    {%- set environment.decollect = decollect %}
{%- endmacro %}
