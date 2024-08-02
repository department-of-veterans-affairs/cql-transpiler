{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro GreaterOrEqualPrint(environment, this, state, arguments) -%}
    ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['left'])}} >= {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }})
{%- endmacro %}

{%- macro GreaterOrEqualStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set GreaterOrEqual = namespace() %}
    {%- set environment.GreaterOrEqual = GreaterOrEqual %}
    {%- do environment.OperatorClass.construct(environment, none, environment.GreaterOrEqual) %}
    {%- set GreaterOrEqual.print = GreaterOrEqualPrint %}
{%- endmacro %}