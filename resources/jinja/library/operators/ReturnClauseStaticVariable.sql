{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ReturnClausePrint(environment, this, state, arguments) -%}
    {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}
{%- endmacro %}

{%- macro ReturnClauseStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ReturnClause = namespace() %}
    {%- set environment.ReturnClause = ReturnClause %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ReturnClause) %}
    {%- set ReturnClause.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set ReturnClause.defaultDataSQLFormat = environment.DataSQLFormatEnum.INHERITED %}
    {%- set ReturnClause.defaultAccessType = environment.AccessTypeEnum.INHERITED %}
    {%- set ReturnClause.print = ReturnClausePrint %}
{%- endmacro %}