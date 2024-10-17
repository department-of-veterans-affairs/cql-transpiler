{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ExpressionRefAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['referenceTo']['operator'].allowsSelectFromAccessType(environment, arguments['referenceTo']['operator'], carrier, state, arguments['referenceTo']) %}
{%- endmacro %}

{%- macro ExpressionRefAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['referenceTo']['operator'].allowsDotPropertyAccessType(environment, arguments['referenceTo']['operator'], carrier, state, arguments['referenceTo']) %}
{%- endmacro %}

{%- macro ExpressionRefPrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['referenceTo']) }}
{%- endmacro %}

{%- macro ExpressionRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ExpressionRef = namespace() %}
    {%- set environment.ExpressionRef = ExpressionRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ExpressionRef) %}
    {%- set ExpressionRef.allowsSelectFromAccessType = ExpressionRefAllowsSelectFromAccessType %}
    {%- set ExpressionRef.allowsDotPropertyAccessType = ExpressionRefAllowsDotPropertyAccessType %}
    {%- set ExpressionRef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set ExpressionRef.print = ExpressionRefPrint %}
{%- endmacro %}