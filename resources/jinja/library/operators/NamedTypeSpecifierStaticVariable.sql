{% from "library/operators/TypeSpecifierClass.sql" import TypeSpecifierClassInit %}

{%- macro NamedTypeSpecifierStaticVariableInit(environment) %}
    {# -initialize prerequisites #}
    {%- do TypeSpecifierClassInit(environment) %}
    {# initialize member variables #}
    {%- set NamedTypeSpecifier = namespace() %}
    {%- set environment.NamedTypeSpecifier = NamedTypeSpecifier %}
    {%- do environment.TypeSpecifierClass.construct(environment, none, environment.NamedTypeSpecifier) %}
    {%- set NamedTypeSpecifier.name = 'NamedTypeSpecifier' %}
{%- endmacro %}