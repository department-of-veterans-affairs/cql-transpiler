{%- from "library/operators/TypeSpecifierClass.sql" import TypeSpecifierClassInit %}

{%- macro TypeSpecifierStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do TypeSpecifierClassInit(environment) %}
    {# initialize member variables #}
    {%- set TypeSpecifier = namespace() %}
    {%- set environment.TypeSpecifier = TypeSpecifier %}
    {%- do environment.TypeSpecifierClass.construct(environment, none, environment.TypeSpecifier) %}
    {%- set TypeSpecifier.name = 'TypeSpecifier' %}
{%- endmacro %}