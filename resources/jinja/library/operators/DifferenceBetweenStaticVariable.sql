{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro DifferenceBetweenPrint(environment, this, state, arguments) -%}
DATEDIFF({{ environment.OperatorHandler.print(environment, this, state, arguments['right'])}}, {{ environment.OperatorHandler.print(environment, this, state, arguments['left']) }})
{%- endmacro %}

{% macro DifferenceBetweenStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set DifferenceBetween = namespace() %}
{%-     set environment.DifferenceBetween = DifferenceBetween %}
{%-     do environment.OperatorClass.construct(environment, none, environment.DifferenceBetween) %}
{%-     set DifferenceBetween.print = DifferenceBetweenPrint %}
{%- endmacro %}