{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro CoalescePrint(environment, this, state, arguments) -%}
    coalesce({{ environment.printOperatorsFromList(environment, state, arguments['children'], ', ') }})
{%- endmacro %}

{%- macro CoalesceStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Coalesce = namespace() %}
    {%- set environment.Coalesce = Coalesce %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Coalesce) %}
    {%- set Coalesce.print = CoalescePrint %}
{%- endmacro %}