{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ConcatenatePrint(environment, this, state, arguments) -%}
concat({{ environment.printOperatorsFromList(environment, state, arguments['children'], ", ") }})
{%- endmacro %}

{% macro ConcatenateStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set Concatenate = namespace() %}
{%-     set environment.Concatenate = Concatenate %}
{%-     do environment.OperatorClass.construct(environment, none, environment.Concatenate) %}
{%-     set Concatenate.print = ConcatenatePrint %}
{%- endmacro %}