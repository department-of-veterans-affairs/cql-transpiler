{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro IfPrint(environment, this, state, arguments) -%}
    {%-     set previousCoercionInstructions = state.coercionInstructions %}
    {%-     set state.coercionInstructions = {} %}
    {#-     conditional statement should not be coerced -#}
    IF ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['condition'])}})
    {%-     set state.coercionInstructions = previousCoercionInstructions -%}
    THEN ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['then'])}}) ELSE ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['else'])}})
{%- endmacro %}

{%- macro IfStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set If = namespace() %}
    {%- set environment.If = If %}
    {%- do environment.OperatorClass.construct(environment, none, environment.If) %}
    {%- set If.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set If.print = IfPrint %}
{%- endmacro %}