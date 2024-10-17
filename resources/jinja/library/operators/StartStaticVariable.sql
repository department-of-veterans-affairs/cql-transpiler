{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro StartAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsSelectFromAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro StartAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsDotPropertyAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro StartPrint(environment, this, state, arguments) -%}
    {%- set dotPropertyCarrier = namespace() %}
    {%- do arguments['child']['operator'].allowsDotPropertyAccessType(environment, arguments['child']['operator'], dotPropertyCarrier, state, arguments['child']) -%}
    {%- if dotPropertyCarrier.value -%}
        {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}.{{ environment.intervalStart }}
    {%- else -%}
        {%- set selectFromCarrier = namespace() %}
        {%- do arguments['child']['operator'].allowsSelectFromAccessType(environment, arguments['child']['operator'], selectFromCarrier, state, arguments['child']) -%}
        {%- if selectFromCarrier.value -%}
            (SELECT {{ environment.intervalStart }} FROM {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
        {%- else -%}
            /* unable to determine access type for child of Start */ -> {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}
        {%- endif %}
    {%- endif %}
{%- endmacro %}

{%- macro StartStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do IntervalStaticVariablesInit(environment) %}
    {#- initialize member variables #}
    {%- set Start = namespace() %}
    {%- set environment.Start = Start %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Start) %}
    {%- set Start.allowsSelectFromAccessType = StartAllowsSelectFromAccessType %}
    {%- set Start.allowsDotPropertyAccessType = StartAllowsDotPropertyAccessType %}
    {%- set Start.print = StartPrint %}
{%- endmacro %}