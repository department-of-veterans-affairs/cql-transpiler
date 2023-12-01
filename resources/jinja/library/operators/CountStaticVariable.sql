{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro CountPrint(environment, this, state, arguments) -%}
    {{ environment.logWarning(" count is untested ") }}count({{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }})
{%- endmacro %}

{%- macro CountStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Count = namespace() %}
    {%- set environment.Count = Count %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Count) %}
    {%- set Count.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW %}
    {%- set Count.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Count.print = CountPrint %}
{%- endmacro %}