{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
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
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do ParameterRefStaticVariableInit(environment) %}
    {#- initialize member variables #}
    {%- set ParameterRef = environment.ParameterRef %}
    {%- set ParameterRef.print = ParameterRefPrintCustomOverride %}
{%- endmacro %}