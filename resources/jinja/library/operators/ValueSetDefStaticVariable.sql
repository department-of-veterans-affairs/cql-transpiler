{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ValueSetDefPrint(environment, this, state, arguments) -%}
    {{ arguments['value'] }}
{%- endmacro %}

{%- macro ValueSetDefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ValueSetDef = namespace() %}
    {%- set environment.ValueSetDef = ValueSetDef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ValueSetDef) %}
    {%- set ValueSetDef.print = ValueSetDefPrint %}
{%- endmacro %}