{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro IntervalPrint(environment, this, state, arguments) -%}
    {%- if arguments['lowClosedExpression'] or arguments['highClosedExpression'] -%}
        {{ environment.logError(environment, "TODO: Support closed intervals") }}
    {%- else -%}
        {{
            "struct("
            ~environment.coerceAndReformat(environment, state, arguments['low']['operator'], arguments['low'], none, none, true, namespace(), namespace())
            ~" AS "
            ~environment.intervalStart
            ~", "
            ~environment.coerceAndReformat(environment, state, arguments['high']['operator'], arguments['high'], none, none, true, namespace(), namespace())
            ~" AS "
            ~environment.intervalEnd
            ~")"
        }}
    {%- endif -%}
{%- endmacro %}

{%- macro IntervalStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do IntervalStaticVariablesInit(environment) %}
    {#- initialize member variables #}
    {%- set Interval = namespace() %}
    {%- set environment.Interval = Interval %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Interval) %}
    {%- set Interval.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Interval.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Interval.defaultAccessType = environment.AccessTypeEnum.INTERVAL %}
    {%- set Interval.print = IntervalPrint %}
{%- endmacro %}