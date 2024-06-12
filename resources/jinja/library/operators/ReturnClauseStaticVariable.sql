{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro ReturnClausePrint(environment, this, state, arguments) -%}
{{ environment.printOperatorsFromList(environment, state, arguments['children'], ", ") }}
{%- endmacro %}

{% macro ReturnClauseStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set ReturnClause = namespace() %}
{%-     set environment.ReturnClause = ReturnClause %}
{%-     do environment.OperatorClass.construct(environment, none, environment.ReturnClause) %}
{%-     set ReturnClause.defaultDataType = environment.DataTypeEnum.UNKNOWN %}
{%-     set ReturnClause.print = ReturnClausePrint %}
{%- endmacro %}