{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ConversionHandlingFunctions.sql" import ConversionHandlingFunctionsInit %}

{%- macro StartPrint(environment, this, state, arguments) -%}
        {{ environment.coerceAndReformat(environment, state, arguments['child']['operator'], arguments['child'], none, none, true, namespace(), namespace()) }}.{{ environment.intervalStart }}
{%- endmacro %}

{%- macro StartStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do IntervalStaticVariablesInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do ConversionHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set Start = namespace() %}
    {%- set environment.Start = Start %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Start) %}
    {%- set Start.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set Start.defaultDataSQLFormat = environment.AccessTypeEnum.INHERITED %}
    {%- set Start.defaultAccessType = environment.AccessTypeEnum.DOT_PROPERTY %}
    {%- set Start.print = StartPrint %}
{%- endmacro %}