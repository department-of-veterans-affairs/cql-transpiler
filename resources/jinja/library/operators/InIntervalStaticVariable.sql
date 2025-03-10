{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro InIntervalPrint(environment, this, state, arguments) -%}
    ({{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }} BETWEEN {# -#}

    {#- Create a new Start and End operator containing the right argument as their children -#}
    {%- set newStartOperatorArguments = namespace() %}
    {%- set newStartOperatorArguments.operator = environment.Start %}
    {%- set newStartOperatorArguments.child = arguments['right'] %}

    {%- set newEndOperatorArguments = namespace() %}
    {%- set newEndOperatorArguments.operator = environment.End %}
    {%- set newEndOperatorArguments.child = arguments['right'] %}

    {#- -#}
    {{ environment.coerceAndReformat(environment, state, environment.Start, newStartOperatorArguments, none, none, true, namespace(), namespace()) }}{# -#}
    {#- #} AND {# -#}
    {{ environment.coerceAndReformat(environment, state, environment.End, newStartOperatorArguments, none, none, true, namespace(), namespace()) }}){# -#}
{%- endmacro %}

{%- macro InIntervalStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do IntervalStaticVariablesInit(environment) %}
    {#- initialize member variables #}
    {%- set InInterval = namespace() %}
    {%- set environment.InInterval = InInterval %}
    {%- do environment.OperatorClass.construct(environment, none, environment.InInterval) %}
    {%- set InInterval.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set InInterval.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set InInterval.print = InIntervalPrint %}
{%- endmacro %}