{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/StandardFunctions.sql" import StandardInit %}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro IntervalPrint(environment, this, state, arguments) -%}
    SELECT struct({{ environment.OperatorHandler.print(environment, this, state, arguments['high'])}} as {{ environment.intervalEnd }}, {{ environment.OperatorHandler.print(environment, this, state, arguments['low']) }} as {{ environment.intervalStart }}) {{ printSingleValueColumnName() }}
{%- endmacro %}

{%- macro IntervalStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardInit(environment) %}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {%- do IntervalStaticVariablesInit(environment) %}
    {#- initialize member variables #}
    {%- set Interval = namespace() %}
    {%- set environment.Interval = Interval %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Interval) %}
    {%- set Interval.defaultDataType = environment.DataTypeEnum.ENCAPSULATED %}
    {%- set Interval.print = IntervalPrint %}
{%- endmacro %}