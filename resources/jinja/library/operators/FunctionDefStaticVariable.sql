{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro FunctionDefPrint(environment, this, state, arguments) -%}
    {%- set previousContext = state.context %}
    {%- if previousContext == none and arguments['context'] %}
        {%- set state.context = arguments['context'] %}
    {%- endif -%}
    {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}
    {%- set state.context = previousContext %}
{%- endmacro %}

{%- macro FunctionDefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set FunctionDef = namespace() %}
    {%- set environment.FunctionDef = FunctionDef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.FunctionDef) %}
    {%- set FunctionDef.defaultDataSQLFormat = environment.DataSQLFormatEnum.INHERITED %}
    {%- set FunctionDef.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set FunctionDef.defaultAccessType = environment.AccessTypeEnum.INHERITED %}
    {%- set FunctionDef.print = FunctionDefPrint %}
{%- endmacro %}