{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro IntervalPrint(environment, this, state, arguments) -%}
    {%- if arguments['lowClosedExpression'] or arguments['highClosedExpression'] -%}
        /* todo -- Interval -- support open intervals */
    {%- else -%}
        struct({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['high'])}} AS {{ environment.intervalEnd }}, {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['low']) }} AS {{ environment.intervalStart }})
    {%- endif -%}
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
    {%- set Interval.allowsSelectFromAccessTypeByDefault = true %}
    {%- set Interval.print = IntervalPrint %}
{%- endmacro %}