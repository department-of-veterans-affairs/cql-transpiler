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
    {%- set carrier = namespace() %}
    {%- do arguments['right']['operator'].getDataType(environment, arguments['right']['operator'], carrier, state, arguments['right']['arguments']) %}
    {%- if carrier.value == environment.DataTypeEnum.SIMPLE -%}
        {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['left'])}} BETWEEN {# -#}
        {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }}.{{ environment.intervalStart }} {# -#}
        AND {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }}.{{ environment.intervalEnd }}
    {%- else -%}
        {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['left'])}} BETWEEN {# -#}
        {%- set previousCoercionInstructions = state.coercionInstructions -%}
        {%- set state.coercionInstructions = { environment.DataTypeEnum.SIMPLE: environment.DataTypeEnum.ENCAPSULATED } -%}
        (SELECT {{ environment.intervalStart }} FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }})) {# -#}
        AND (SELECT {{ environment.intervalEnd }} FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }}))
        {%- set state.coercionInstructions = previousCoercionInstructions %}
    {%- endif %}
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