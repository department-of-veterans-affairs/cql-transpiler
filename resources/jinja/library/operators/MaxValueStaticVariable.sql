{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro MaxValuePrint(environment, this, state, arguments) -%}
    MAXVALUE
{%- endmacro %}

{%- macro MaxValueStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set MaxValue = namespace() %}
    {%- set environment.MaxValue = MaxValue %}
    {%- do environment.OperatorClass.construct(environment, none, environment.MaxValue) %}
    {%- set MaxValue.print = MaxValuePrint %}
{%- endmacro %}