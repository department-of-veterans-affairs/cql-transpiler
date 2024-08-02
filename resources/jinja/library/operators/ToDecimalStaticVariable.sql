{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ToDecimalPrint(environment, this, state, arguments) -%}
    (0.0 + {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
{%- endmacro %}

{%- macro ToDecimalStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set ToDecimal = namespace() %}
    {%- set environment.ToDecimal = ToDecimal %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ToDecimal) %}
    {%- set ToDecimal.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set ToDecimal.print = ToDecimalPrint %}
{%- endmacro %}