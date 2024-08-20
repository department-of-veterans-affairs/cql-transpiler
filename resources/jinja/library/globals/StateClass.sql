{%- macro StateClassConstruct(environment, stateNamespace) %}
    {%- set stateNamespace.coercionInstructions = {} %}
    {%- set stateNamespace.context = none %}
    {%- set stateNamespace.insideQuery = false %}
    {%- set stateNamespace.functionArguments = {} %}
    {%- set stateNamespace.insideSqlComment = false %}
{%- endmacro %}

{%- macro StateClassInit(environment) %}
    {%- set StateClass = namespace() %}
    {%- set StateClass.construct = StateClassConstruct %}
    {%- set environment.StateClass = StateClass %}
{%- endmacro %}