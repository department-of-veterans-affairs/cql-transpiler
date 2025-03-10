{%- macro StateClassConstruct(environment, stateNamespace) %}
    {%- set stateNamespace.context = none %}
    {%- set stateNamespace.functionArguments = {} %}
    {%- set stateNamespace.aliasContext = none %}
{%- endmacro %}

{%- macro StateClassInit(environment) %}
    {%- set StateClass = namespace() %}
    {%- set StateClass.construct = StateClassConstruct %}
    {%- set environment.StateClass = StateClass %}
{%- endmacro %}