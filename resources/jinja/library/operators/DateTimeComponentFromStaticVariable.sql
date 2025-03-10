{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro DateTimeComponentFromPrint(environment, this, state, arguments) -%}
    DATEPART({{ arguments['precision']}}, {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }})
{%- endmacro %}

{%- macro DateTimeComponentFromStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set DateTimeComponentFrom = namespace() %}
    {%- set environment.DateTimeComponentFrom = DateTimeComponentFrom %}
    {%- do environment.OperatorClass.construct(environment, none, environment.DateTimeComponentFrom) %}
    {%- set DateTimeComponentFrom.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW %}
    {%- set DateTimeComponentFrom.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set DateTimeComponentFrom.print = DateTimeComponentFromPrint %}
{%- endmacro %}