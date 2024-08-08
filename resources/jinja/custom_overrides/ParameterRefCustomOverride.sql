{#    
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * ParameterRefStaticVariable.sql
#}
{%- from "jinja/library/operators/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "jinja/library/operators/ParameterRefStaticVariable.sql" import ParameterRefStaticVariableInit %}

{%- macro ParameterRefPrintCustomOverride(environment, this, state, arguments) -%}
{%- endmacro %}

{%- macro ParameterRefCustomOverrideInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do ParameterRefStaticVariableInit(environment) %}
    {# initialize member variables #}
    {%- set ParameterRef = environment.ParameterRef %}
    {%- set ParameterRef.print = ParameterRefPrintCustomOverride %}
{%- endmacro %}