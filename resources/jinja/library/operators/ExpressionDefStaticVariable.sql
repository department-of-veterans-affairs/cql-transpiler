{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ExpressionDefAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsSelectFromAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro ExpressionDefAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsDotPropertyAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro ExpressionDefPrint(environment, this, state, arguments) %}
    {%- set previousContext = state.context %}
    {%- if previousContext == none and arguments['context'] != none %}
        {%- set state.context = arguments['context'] %}
    {%- endif -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}
    {%- set state.context = previousContext %}
{%- endmacro %}

{%- macro ExpressionDefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ExpressionDef = namespace() %}
    {%- set environment.ExpressionDef = ExpressionDef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ExpressionDef) %}
    {%- set ExpressionDef.allowsSelectFromAccessType = ExpressionDefAllowsSelectFromAccessType %}
    {%- set ExpressionDef.allowsDotPropertyAccessType = ExpressionDefAllowsDotPropertyAccessType %}
    {%- set ExpressionDef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set ExpressionDef.print = ExpressionDefPrint %}
{%- endmacro %}