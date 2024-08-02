{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro IntervalPrint(environment, this, state, arguments) -%}
    SELECT struct({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['high'])}} as {{ environment.intervalEnd }}, {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['low']) }} as {{ environment.intervalStart }}) {{ environment.printSingleValueColumnName() }}
{%- endmacro %}

{%- macro IntervalStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardFunctionsInit(environment) %}
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