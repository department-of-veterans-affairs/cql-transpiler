{#    
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * ValueSetRefStaticVariable.sql
#}
{%- from "jinja_transpilation_libraries/sparksql/default/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "jinja_transpilation_libraries/sparksql/default/ValueSetRefStaticVariable.sql" import ValueSetRefStaticVariableInit %}

{%- macro ValueSetRefPrintCustomOverride(environment, this, state, arguments) -%}
    {{ arguments['reference'].split(':')[2] }}
{%- endmacro %}

{%- macro ValueSetRefCustomOverrideInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do ValueSetRefStaticVariableInit(environment) %}
    {# initialize member variables #}
    {%- set ValueSetRef = environment.ValueSetRef %}
    {%- set ValueSetRef.print = ValueSetRefPrintCustomOverride %}
{%- endmacro %}