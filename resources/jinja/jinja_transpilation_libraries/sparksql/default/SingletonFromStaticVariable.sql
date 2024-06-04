{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
#}
{% from "jinja_transpilation_libraries/sparksql/default/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{% from "jinja_transpilation_libraries/sparksql/default/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "jinja_transpilation_libraries/sparksql/default/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro printSingletonFrom(environment, this, state, arguments) %}
{{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }}
{%- endmacro %}

{% macro SingletonFromStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set SingletonFrom = namespace() %}
{%-     do environment.OperatorClass.construct(environment, none, SingletonFrom) %}
{%-     set SingletonFrom.print = printSingletonFrom %}
{%-     set environment.SingletonFrom = SingletonFrom %}
{%- endmacro %}