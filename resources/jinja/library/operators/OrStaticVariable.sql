{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro OrPrint(environment, this, state, arguments) -%}
    ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['left'])}} OR {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right']) }})
{%- endmacro %}

{%- macro OrStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Or = namespace() %}
    {%- set environment.Or = Or %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Or) %}
    {%- set Or.print = OrPrint %}
{%- endmacro %}