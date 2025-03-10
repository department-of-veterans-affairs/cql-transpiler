{%- from "library/operators/TypeSpecifierClass.sql" import TypeSpecifierClassInit %}

{%- macro IntervalTypeSpecifierStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do TypeSpecifierClassInit(environment) %}
    {#- initialize member variables #}
    {%- set IntervalTypeSpecifier = namespace() %}
    {%- set environment.IntervalTypeSpecifier = IntervalTypeSpecifier %}
    {%- do environment.TypeSpecifierClass.construct(environment, none, environment.IntervalTypeSpecifier) %}
    {%- set IntervalTypeSpecifier.name = 'IntervalTypeSpecifier' %}
{%- endmacro %}