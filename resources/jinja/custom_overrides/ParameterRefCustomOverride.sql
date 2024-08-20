{#    
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * ParameterRefStaticVariable.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/operators/ParameterRefStaticVariable.sql" import ParameterRefStaticVariableInit %}

{%- macro ParameterRefPrintCustomOverride(environment, this, state, arguments) -%}/* Parameter reference with args: <{{ arguments }}>*/{%- endmacro %}

{%- macro ParameterRefCustomOverrideInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do ParameterRefStaticVariableInit(environment) %}
    {# initialize member variables #}
    {%- set ParameterRef = environment.ParameterRef %}
    {%- set ParameterRef.print = ParameterRefPrintCustomOverride %}
{%- endmacro %}