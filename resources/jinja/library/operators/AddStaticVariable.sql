{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro AddPrint(environment, this, state, arguments) %}
({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['left'])}} + {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right'])}})
{%- endmacro %}

{% macro AddStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set Add = namespace() %}
{%-     set environment.Add = Add %}
{%-     do environment.OperatorClass.construct(environment, none, environment.Add) %}
{%-     set Add.print = AddPrint %}
{%- endmacro %}