{#    
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * ParameterRefStaticVariable.sql
#}
{%- from "jinja_transpilation_libraries/sparksql/default/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "jinja_transpilation_libraries/sparksql/default/ParameterRefStaticVariable.sql" import ParameterRefStaticVariableInit %}

{%- macro ParameterRefPrintCustomOverride(environment, this, state, arguments) -%}
    {%- if arguments['name'] == 'Measurement_Period' -%}
        measurementPeriod
    {%- else -%}
        {{ arguments['name'] }}
    {%- endif %}
{%- endmacro %}

{%- macro ParameterRefCustomOverrideInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do ParameterRefStaticVariableInit(environment) %}
    {# initialize member variables #}
    {%- set ParameterRef = environment.ParameterRef %}
    {%- set ParameterRef.print = ParameterRefPrintCustomOverride %}
{%- endmacro %}