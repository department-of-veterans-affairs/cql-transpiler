{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
#}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro SingletonFromPrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}
{%- endmacro %}

{%- macro SingletonFromStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set SingletonFrom = namespace() %}
    {%- do environment.OperatorClass.construct(environment, none, SingletonFrom) %}
    {%- set SingletonFrom.print = SingletonFromPrint %}
    {%- set environment.SingletonFrom = SingletonFrom %}
{%- endmacro %}