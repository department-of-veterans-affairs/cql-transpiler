{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ParameterRefPrint(environment, this, state, arguments) -%}
    {{ environment.logError(environment, "TODO: ParameterRef -- "~arguments['name']) }}
{%- endmacro %}

{%- macro ParameterRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ParameterRef = namespace() %}
    {%- set environment.ParameterRef = ParameterRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ParameterRef) %}
    {%- set ParameterRef.print = ParameterRefPrint %}
{%- endmacro %}