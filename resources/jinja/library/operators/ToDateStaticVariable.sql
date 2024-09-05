{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ToDatePrint(environment, this, state, arguments) -%}
    /* Unsupported operator ToDate with arguments {{ arguments }} */
{%- endmacro %}

{%- macro ToDateStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set ToDate = namespace() %}
    {%- set environment.ToDate = ToDate %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ToDate) %}
    {%- set ToDate.print = ToDatePrint %}
{%- endmacro %}