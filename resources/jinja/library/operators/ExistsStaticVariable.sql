{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ExistsPrint(environment, this, state, arguments) -%}
    EXISTS ({{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child'])}})
{%- endmacro %}

{%- macro ExistsStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Exists = namespace() %}
    {%- set environment.Exists = Exists %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Exists) %}
    {%- set Exists.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Exists.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Exists.print = ExistsPrint %}
{%- endmacro %}