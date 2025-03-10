{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro DateFromPrint(environment, this, state, arguments) -%}
    {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}
{%- endmacro %}

{%- macro DateFromStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set DateFrom = namespace() %}
    {%- set environment.DateFrom = DateFrom %}
    {%- do environment.OperatorClass.construct(environment, none, environment.DateFrom) %}
    {%- set DateFrom.defaultDataSQLFormat = environment.DataSQLFormatEnum.INHERITED %}
    {%- set DateFrom.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set DateFrom.print = DateFromPrint %}
{%- endmacro %}