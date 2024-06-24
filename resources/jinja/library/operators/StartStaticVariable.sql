{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro StartPrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }}.{{ environment.intervalStart }}
{%- endmacro %}

{%- macro StartStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {%- do IntervalStaticVariablesInit(environment) %}
    {#- initialize member variables #}
    {%- set Start = namespace() %}
    {%- set environment.Start = Start %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Start) %}
    {%- set Start.print = StartPrint %}
{%- endmacro %}