{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro MinValuePrint(environment, this, state, arguments) -%}
    MINVALUE
{%- endmacro %}

{%- macro MinValueStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set MinValue = namespace() %}
    {%- set environment.MinValue = MinValue %}
    {%- do environment.OperatorClass.construct(environment, none, environment.MinValue) %}
    {%- set MinValue.print = MinValuePrint %}
{%- endmacro %}