{#
    Environment prerequisites:
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "jinja_transpilation_libraries/sparksql/default/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "jinja_transpilation_libraries/sparksql/default/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{% macro RetrieveStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set Retrieve = namespace() %}
{%-     set Retrieve.defaultDataType = environment.DataTypeEnum.TABLE %}
{%-     do environment.OperatorClass.construct(environment, none, Retrieve) %}
{%-     set environment.Retrieve = Retrieve %}
{%- endmacro %}