{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro TimezoneOffsetFromPrint(environment, this, state, arguments) -%}
    DATEPART(TZOFFSET, {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
{%- endmacro %}

{%- macro TimezoneOffsetFromStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set TimezoneOffsetFrom = namespace() %}
    {%- set environment.TimezoneOffsetFrom = TimezoneOffsetFrom %}
    {%- do environment.OperatorClass.construct(environment, none, environment.TimezoneOffsetFrom) %}
    {%- set TimezoneOffsetFrom.print = TimezoneOffsetFromPrint %}
{%- endmacro %}