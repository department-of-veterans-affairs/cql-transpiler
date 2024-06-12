{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro LetClausePrint(environment, this, state, arguments) -%}
{{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }}
{%- endmacro %}

{% macro LetClauseStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set LetClause = namespace() %}
{%-     set environment.LetClause = LetClause %}
{%-     do environment.OperatorClass.construct(environment, none, environment.LetClause) %}
{%-     set LetClause.defaultDataType = environment.DataTypeEnum.INHERITED %}
{%-     set LetClause.print = LetClausePrint %}
{%- endmacro %}