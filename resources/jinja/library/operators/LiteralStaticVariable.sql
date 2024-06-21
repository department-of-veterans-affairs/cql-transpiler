{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro LiteralPrint(environment, this, state, arguments) -%}
    {%- if arguments['type'] == 'Integer' -%}
        {{ arguments['value'] }}
    {%- elif arguments['type'] == 'String' -%}
        '{{ arguments['value'] }}'
    {%- else -%}
        {{ arguments['type'] }}::{{ arguments['value'] }}
    {%- endif %}
{%- endmacro %}

{%- macro LiteralStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Literal = namespace() %}
    {%- set environment.Literal = Literal %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Literal) %}
    {%- set Literal.print = LiteralPrint %}
{%- endmacro %}