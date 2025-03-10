{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ConversionHandlingFunctions.sql" import ConversionHandlingFunctionsInit %}

{%- macro EndPrint(environment, this, state, arguments) -%}
        {{ environment.coerceAndReformat(environment, state, arguments['child']['operator'], arguments['child'], none, none, true, namespace(), namespace()) }}.{{ environment.intervalEnd }}
{%- endmacro %}

{%- macro EndStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do IntervalStaticVariablesInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do ConversionHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set End = namespace() %}
    {%- set environment.End = End %}
    {%- do environment.OperatorClass.construct(environment, none, environment.End) %}
    {%- set End.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set End.defaultDataSQLFormat = environment.AccessTypeEnum.INHERITED %}
    {%- set End.defaultAccessType = environment.AccessTypeEnum.DOT_PROPERTY %}
    {%- set End.print = EndPrint %}
{%- endmacro %}