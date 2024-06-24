{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro LessOrEqualPrint(environment, this, state, arguments) -%}
    ({{ environment.OperatorHandler.print(environment, this, state, arguments['left']) }} <= {{ environment.OperatorHandler.print(environment, this, state, arguments['right']) }})
{%- endmacro %}

{%- macro LessOrEqualStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set LessOrEqual = namespace() %}
    {%- set environment.LessOrEqual = LessOrEqual %}
    {%- do environment.OperatorClass.construct(environment, none, environment.LessOrEqual) %}
    {%- set LessOrEqual.print = LessOrEqualPrint %}
{%- endmacro %}