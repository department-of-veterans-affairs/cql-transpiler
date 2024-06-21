{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro InPrint(environment, this, state, arguments) -%}
    ({{ environment.OperatorHandler.print(environment, this, state, arguments['left'])}} IN {{ environment.OperatorHandler.print(environment, this, state, arguments['right']) }})
{%- endmacro %}

{%- macro InStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set In = namespace() %}
    {%- set environment.In = In %}
    {%- do environment.OperatorClass.construct(environment, none, environment.In) %}
    {%- set In.print = InPrint %}
{%- endmacro %}