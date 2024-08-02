{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro MultiplyPrint(environment, this, state, arguments) -%}
    ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['left']) }} * {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }})
{%- endmacro %}

{%- macro MultiplyStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Multiply = namespace() %}
    {%- set environment.Multiply = Multiply %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Multiply) %}
    {%- set Multiply.print = MultiplyPrint %}
{%- endmacro %}