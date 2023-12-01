{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro LiteralPrint(environment, this, state, arguments) -%}
    {%- if arguments['type'] == 'Integer' -%}
        {{ arguments['value'] }}
    {%- elif arguments['type'] == 'String' -%}
        '{{ arguments['value'] }}'
    {%- else -%}
        {{ environment.logError(environment, "TODO: Support Literal of form "~arguments['type']~"::"~arguments['value']) }}
    {%- endif %}
{%- endmacro %}

{%- macro LiteralStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Literal = namespace() %}
    {%- set environment.Literal = Literal %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Literal) %}
    {%- set Literal.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Literal.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Literal.print = LiteralPrint %}
{%- endmacro %}