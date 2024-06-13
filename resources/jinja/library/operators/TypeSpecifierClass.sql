{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro TypeSpecifierClassPrint(environment, this, state, arguments) -%}
{%      set previousInsideSqlComment = state.insideSqlComment %}
{%-     set state.insideSqlComment = true %}
{%-     if not previousInsideSqlComment %}
/*
{%-     endif %}
 Type specifier operator: <{{ this.name }}> with children:[{{ environment.printOperatorsFromList(environment, state, arguments['children'], ", ") }}] 
{%-     if not previousInsideSqlComment %}
*/
{%-     endif %}
{%-     set state.insideSqlComment = previousInsideSqlComment %}
{%- endmacro %}

{% macro TypeSpecifierClassConstruct(environment, state, typeSpecifierNamestate) %}
{%-     do environment.OperatorClass.construct(environment, state, typeSpecifierNamestate) %}
{%-     set typeSpecifierNamestate.print = TypeSpecifierPrint %}
{% endmacro %}

{% macro TypeSpecifierClassInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set TypeSpecifierClass = namespace() %}
{%-     set TypeSpecifierClass.construct = TypeSpecifierClassConstruct %}
{%-     set environment.TypeSpecifierClass = TypeSpecifierClass %}
{%- endmacro %}