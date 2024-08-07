{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro DateTimeComponentFromPrint(environment, this, state, arguments) -%}
    DATEPART({{ arguments['precision']}}, {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
{%- endmacro %}

{%- macro DateTimeComponentFromStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set DateTimeComponentFrom = namespace() %}
    {%- set environment.DateTimeComponentFrom = DateTimeComponentFrom %}
    {%- do environment.OperatorClass.construct(environment, none, environment.DateTimeComponentFrom) %}
    {%- set DateTimeComponentFrom.print = DateTimeComponentFromPrint %}
{%- endmacro %}