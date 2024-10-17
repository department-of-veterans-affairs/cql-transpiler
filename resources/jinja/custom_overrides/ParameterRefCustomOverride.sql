{%- from "library/operators/ParameterRefStaticVariable.sql" import ParameterRefStaticVariableInit %}

{%- macro ParameterRefPrintCustomOverride(environment, this, state, arguments) -%}
    {%- set handledParameters = { 'Measurement_Period': 'measurementPeriod' } %}
    {%- if handledParameters[arguments['name']] is defined -%}
        _parameters.{{ handledParameters[arguments['name']] }}
    {%- else -%}
        /* Parameter reference with args: <{{ arguments }}>*/
    {%- endif %}
{%- endmacro %}

{%- macro ParameterRefCustomOverrideInit(environment) %}
    {#- initialize prerequisites #}
    {%- do ParameterRefStaticVariableInit(environment) %}
    {#- initialize member variables #}
    {%- set ParameterRef = environment.ParameterRef %}
    {#- TODO: is there some way to programatically determine the possible access types of a parameter? -#}
    {%- set ParameterRef.allowsDotPropertyAccessTypeByDefault = true %}
    {%- set ParameterRef.allowsSelectFromAccessTypeByDefault = true %}
    {%- set ParameterRef.print = ParameterRefPrintCustomOverride %}
{%- endmacro %}