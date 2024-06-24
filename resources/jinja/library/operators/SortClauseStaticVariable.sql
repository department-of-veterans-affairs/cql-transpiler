{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro SortClausePrint(environment, this, state, arguments) -%}
    ORDER BY {{ environment.printOperatorsFromList(environment, state, arguments['children'], ", ") }}
{%- endmacro %}

{%- macro SortClauseStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set SortClause = namespace() %}
    {%- set environment.SortClause = SortClause %}
    {%- do environment.OperatorClass.construct(environment, none, environment.SortClause) %}
    {%- set SortClause.defaultDataType = environment.DataTypeEnum.STATEMENT %}
    {%- set SortClause.print = SortClausePrint %}
{%- endmacro %}