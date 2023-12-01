{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro InValueSetPrint(environment, this, state, arguments) -%}
    {%- if arguments['valueSetExpression'] -%}
        {%- set valueSet = arguments['valueSetExpression'] %}
    {%- else -%}
        {%- set valueSet = arguments['valueSetReference'] %}
    {%- endif -%}
    {{ environment.logError(environment, "TODO: InValueSet") }}
{%- endmacro %}

{%- macro InValueSetStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set InValueSet = namespace() %}
    {%- set environment.InValueSet = InValueSet %}
    {%- do environment.OperatorClass.construct(environment, none, environment.InValueSet) %}
    {%- set InValueSet.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW %}
    {%- set InValueSet.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set InValueSet.print = InValueSetPrint %}
{%- endmacro %}