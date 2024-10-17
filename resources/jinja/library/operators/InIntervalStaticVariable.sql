{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro InIntervalPrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['left'])}} BETWEEN {# -#}
    {#- determine how to retrieve information from the interval #}
    {%- set dotPropertyAccessCarrier = namespace() %}
    {%- do arguments['right']['operator'].allowsDotPropertyAccessType(environment, arguments['right']['operator'], dotPropertyAccessCarrier, state, arguments['right']) %}
    {%- if dotPropertyAccessCarrier.value -%}
        {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }}.{{ environment.intervalStart }} {# -#}
        AND {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }}.{{ environment.intervalEnd }}
    {%- else -%}
        {%- set selectFromAccessCarrier = namespace() %}
        {%- do arguments['right']['operator'].allowsSelectFromAccessType(environment, arguments['right']['operator'], selectFromAccessCarrier, state, arguments['right']) %}
        {%- if selectFromAccessCarrier.value -%}
            (SELECT {{ environment.intervalStart }} FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }})) {# -#}
            AND (SELECT {{ environment.intervalEnd }} FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }}))
        {%- else -%}
            {#- We need to coerce the right operator -#}
            {%- set dataTypeCarrier = namespace() %}
            {%- do arguments['right']['operator'].getDataType(environment, arguments['right']['operator'], dataTypeCarrier, state, arguments['right']) -%}
            /* todo -- InInterval support for intervals with no set access type and data type {{ dataTypeCarrier.value }} */ -> {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }}
        {%- endif -%}
    {%- endif -%}
{%- endmacro %}

{%- macro InIntervalStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do IntervalStaticVariablesInit(environment) %}
    {#- initialize member variables #}
    {%- set InInterval = namespace() %}
    {%- set environment.InInterval = InInterval %}
    {%- do environment.OperatorClass.construct(environment, none, environment.InInterval) %}
    {%- set InInterval.print = InIntervalPrint %}
{%- endmacro %}