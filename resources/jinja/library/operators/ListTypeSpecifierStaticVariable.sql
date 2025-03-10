{%- from "library/operators/TypeSpecifierClass.sql" import TypeSpecifierClassInit %}

{%- macro ListTypeSpecifierStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do TypeSpecifierClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ListTypeSpecifier = namespace() %}
    {%- set environment.ListTypeSpecifier = ListTypeSpecifier %}
    {%- do environment.TypeSpecifierClass.construct(environment, none, environment.ListTypeSpecifier) %}
    {%- set ListTypeSpecifier.name = 'ListTypeSpecifier' %}
{%- endmacro %}