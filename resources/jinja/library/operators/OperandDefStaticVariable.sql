{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro OperandDefPrint(environment, this, state, arguments) -%}
    {{ arguments['referencedName'] }}
{%- endmacro %}

{%- macro OperandDefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set OperandDef = namespace() %}
    {%- set environment.OperandDef = OperandDef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.OperandDef) %}
    {%- set OperandDef.defaultDataSQLFormat = environment.DataSQLFormatEnum.INHERITED %}
    {%- set OperandDef.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set OperandDef.defaultAccessType = environment.AccessTypeEnum.INHERITED %}
    {%- set OperandDef.print = OperandDefPrint %}
{%- endmacro %}