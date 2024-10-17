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
    {%- set OperandDef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set OperandDef.print = OperandDefPrint %}
{%- endmacro %}