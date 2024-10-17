{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro EndAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsSelectFromAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro EndAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsDotPropertyAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro EndPrint(environment, this, state, arguments) -%}
    {%- set dotPropertyCarrier = namespace() %}
    {%- do arguments['child']['operator'].allowsDotPropertyAccessType(environment, arguments['child']['operator'], dotPropertyCarrier, state, arguments['child']) -%}
    {%- if dotPropertyCarrier.value -%}
        {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}.{{ environment.intervalEnd }}
    {%- else -%}
        {%- set selectFromCarrier = namespace() %}
        {%- do arguments['child']['operator'].allowsSelectFromAccessType(environment, arguments['child']['operator'], selectFromCarrier, state, arguments['child']) -%}
        {%- if selectFromCarrier.value -%}
            (SELECT {{ environment.intervalEnd }} FROM {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
        {%- else -%}
            /* unable to determine access type for child of End */ -> {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}
        {%- endif %}
    {%- endif %}
{%- endmacro %}

{%- macro EndStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do IntervalStaticVariablesInit(environment) %}
    {#- initialize member variables #}
    {%- set End = namespace() %}
    {%- set environment.End = End %}
    {%- do environment.OperatorClass.construct(environment, none, environment.End) %}
    {%- set End.allowsSelectFromAccessType = EndAllowsSelectFromAccessType %}
    {%- set End.allowsDotPropertyAccessType = EndAllowsDotPropertyAccessType %}
    {%- set End.print = EndPrint %}
{%- endmacro %}