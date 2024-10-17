{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro FunctionDefAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsSelectFromAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro FunctionDefAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsDotPropertyAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro FunctionDefPrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}
{%- endmacro %}

{%- macro FunctionDefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set FunctionDef = namespace() %}
    {%- set environment.FunctionDef = FunctionDef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.FunctionDef) %}
    {%- set FunctionDef.allowsSelectFromAccessType = FunctionDefAllowsSelectFromAccessType %}
    {%- set FunctionDef.allowsDotPropertyAccessType = FunctionDefAllowsDotPropertyAccessType %}
    {%- set FunctionDef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set FunctionDef.print = FunctionDefPrint %}
{%- endmacro %}