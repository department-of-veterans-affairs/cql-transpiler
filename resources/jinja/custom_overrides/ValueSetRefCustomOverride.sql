{#    
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * ValueSetRefStaticVariable.sql
#}
{%- from "jinja/library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "jinja/library/operators/ValueSetRefStaticVariable.sql" import ValueSetRefStaticVariableInit %}

{%- macro ValueSetRefPrintCustomOverride(environment, this, state, arguments) -%}
    {{ arguments['referenceTo'][value].split(':')[2] }}
{%- endmacro %}

{%- macro ValueSetRefCustomOverrideInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do ValueSetRefStaticVariableInit(environment) %}
    {# initialize member variables #}
    {%- set ValueSetRef = environment.ValueSetRef %}
    {%- set ValueSetRef.print = ValueSetRefPrintCustomOverride %}
{%- endmacro %}