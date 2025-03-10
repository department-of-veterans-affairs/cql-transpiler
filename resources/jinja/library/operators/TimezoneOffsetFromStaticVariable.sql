{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro TimezoneOffsetFromPrint(environment, this, state, arguments) -%}
    DATEPART(TZOFFSET, {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }})
{%- endmacro %}

{%- macro TimezoneOffsetFromStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set TimezoneOffsetFrom = namespace() %}
    {%- set environment.TimezoneOffsetFrom = TimezoneOffsetFrom %}
    {%- do environment.OperatorClass.construct(environment, none, environment.TimezoneOffsetFrom) %}
    {%- set TimezoneOffsetFrom.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set TimezoneOffsetFrom.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set TimezoneOffsetFrom.print = TimezoneOffsetFromPrint %}
{%- endmacro %}