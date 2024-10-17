{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ReturnClausePrint(environment, this, state, arguments) -%}
    {{ environment.printOperatorsFromList(environment, state, arguments['children'], ", ") }}
{%- endmacro %}

{%- macro ReturnClauseStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ReturnClause = namespace() %}
    {%- set environment.ReturnClause = ReturnClause %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ReturnClause) %}
    {%- set ReturnClause.defaultDataType = environment.DataTypeEnum.UNDETERMINED %}
    {%- set ReturnClause.print = ReturnClausePrint %}
{%- endmacro %}