{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro SortClausePrint(environment, this, state, arguments) -%}
    ORDER BY {{ environment.printOperatorsFromList(environment, state, arguments['children'], ", ") }}
{%- endmacro %}

{%- macro SortClauseStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set SortClause = namespace() %}
    {%- set environment.SortClause = SortClause %}
    {%- do environment.OperatorClass.construct(environment, none, environment.SortClause) %}
    {%- set SortClause.defaultDataSQLFormat = environment.DataSQLFormatEnum.NOT_ACCESSIBLE %}
    {%- set SortClause.print = SortClausePrint %}
{%- endmacro %}