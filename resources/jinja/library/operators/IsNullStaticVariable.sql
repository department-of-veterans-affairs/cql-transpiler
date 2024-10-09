{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro IsNullPrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }} IS NULL
{%- endmacro %}

{%- macro IsNullStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set IsNull = namespace() %}
    {%- set environment.IsNull = IsNull %}
    {%- do environment.OperatorClass.construct(environment, none, environment.IsNull) %}
    {%- set IsNull.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set IsNull.print = IsNullPrint %}
{%- endmacro %}