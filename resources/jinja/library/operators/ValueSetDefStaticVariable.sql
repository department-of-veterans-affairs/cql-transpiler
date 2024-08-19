{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ValueSetDefPrint(environment, this, state, arguments) -%}
    {{ arguments['value'] }}
{%- endmacro %}

{%- macro ValueSetDefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set ValueSetDef = namespace() %}
    {%- set environment.ValueSetDef = ValueSetDef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ValueSetDef) %}
    {%- set ValueSetDef.print = ValueSetDefPrint %}
{%- endmacro %}