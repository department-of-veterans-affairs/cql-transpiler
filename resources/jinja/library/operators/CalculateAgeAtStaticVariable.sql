{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro CalculateAgeAtPrint(environment, this, state, arguments) -%}
    floor(months_between({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['right'])}}, {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['left']) }}) / 12)
{%- endmacro %}

{%- macro CalculateAgeAtStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set CalculateAgeAt = namespace() %}
    {%- set environment.CalculateAgeAt = CalculateAgeAt %}
    {%- do environment.OperatorClass.construct(environment, none, environment.CalculateAgeAt) %}
    {%- set CalculateAgeAt.print = CalculateAgeAtPrint %}
{%- endmacro %}