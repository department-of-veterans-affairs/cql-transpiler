{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro AndPrint(environment, this, state, arguments) -%}
({{ environment.OperatorHandler.print(environment, this, state, arguments['left'])}} AND {{ environment.OperatorHandler.print(environment, this, state, arguments['right']) }})
{%- endmacro %}

{% macro AndStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-     do OperatorHandlerStaticVariableInit(environment) %}
{%-     do OperatorClassInit(environment) %}
{%-     do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set And = namespace() %}
{%-     set environment.And = And %}
{%-     do environment.OperatorClass.construct(environment, none, environment.And) %}
{%-     set And.print = AndPrint %}
{%- endmacro %}