{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro OperandDefPrint(environment, this, state, arguments) -%}
    {{ arguments['name'] }}
{%- endmacro %}

{%- macro OperandDefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set OperandDef = namespace() %}
    {%- set environment.OperandDef = OperandDef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.OperandDef) %}
    {%- set OperandDef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set OperandDef.print = OperandDefPrint %}
{%- endmacro %}