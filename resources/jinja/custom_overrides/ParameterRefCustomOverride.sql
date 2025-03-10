{%- from "library/operators/ParameterRefStaticVariable.sql" import ParameterRefStaticVariableInit %}

{%- macro ParameterRefPrintCustomOverride(environment, this, state, arguments) -%}
    {%- set handledParameters = { 'Measurement_Period': 'measurementPeriod' } %}
    {%- if handledParameters[arguments['name']] is defined -%}
        _parameters.{{ handledParameters[arguments['name']] }}
    {%- else -%}
        {{ environment.logError(environment, "TODO: ParameterRef -- "~arguments['name']) }}
    {%- endif %}
{%- endmacro %}

{%- macro ParameterRefCustomOverrideInit(environment) %}
    {#- initialize prerequisites #}
    {%- do ParameterRefStaticVariableInit(environment) %}
    {#- initialize member variables #}
    {%- set ParameterRef = environment.ParameterRef %}
    {#- TODO: is there some way to programatically determine the data state, access type, and format of a parameter? -#}
    {%- set ParameterRef.defaultDataSQLFormat = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE_REFERENCE %}
    {%- set ParameterRef.defaultDataState = environment.DataStateEnum.ENCAPSULATED %}
    {%- set ParameterRef.defaultAccessType = environment.AccessTypeEnum.DOT_PROPERTY %}
    {%- set ParameterRef.print = ParameterRefPrintCustomOverride %}
{%- endmacro %}