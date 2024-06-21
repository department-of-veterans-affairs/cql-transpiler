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

{%- macro InIntervalPrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, this, state, arguments['left'])}} BETWEEN SELECT {{ environment.intervalStart }} FROM {{ environment.OperatorHandler.print(environment, this, state, arguments['right']) }} AND SELECT {{ environment.intervalEnd }} FROM {{ environment.OperatorHandler.print(environment, this, state, arguments['right']) }}
{%- endmacro %}

{%- macro InIntervalStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {%- do IntervalStaticVariablesInit(environment) %}
    {#- initialize member variables #}
    {%- set InInterval = namespace() %}
    {%- set environment.InInterval = InInterval %}
    {%- do environment.OperatorClass.construct(environment, none, environment.InInterval) %}
    {%- set InInterval.print = InIntervalPrint %}
{%- endmacro %}