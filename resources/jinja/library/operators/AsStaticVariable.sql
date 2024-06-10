{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{% macro AsPrint(environment, this, state, arguments) -%}
{#     arguments['typeSpecifier'] is unused -#}
{{ environment.OperatorHandler.print(environment, this, state, arguments['child'])}}
{%- endmacro %}

{% macro AsStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set As = namespace() %}
{%-     set environment.As = As %}
{%-     do environment.OperatorClass.construct(environment, none, environment.As) %}
{%-     set As.print = AsPrint %}
{%- endmacro %}