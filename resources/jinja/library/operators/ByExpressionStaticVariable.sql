{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ByExpressionPrint(environment, this, state, arguments) -%}
    {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }} {{ arguments['direction'] }}
{%- endmacro %}

{%- macro ByExpressionStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ByExpression = namespace() %}
    {%- set environment.ByExpression = ByExpression %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ByExpression) %}
    {%- set ByExpression.print = ByExpressionPrint %}
{%- endmacro %}