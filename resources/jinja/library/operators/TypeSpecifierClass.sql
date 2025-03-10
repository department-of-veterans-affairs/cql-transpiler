{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro TypeSpecifierClassPrint(environment, this, state, arguments) -%}
    /* Type specifier operator: <{{ this.name }}> with arguments: {{ arguments }} */
{%- endmacro %}

{%- macro TypeSpecifierClassConstruct(environment, state, typeSpecifierNamestate) %}
    {%- do environment.OperatorClass.construct(environment, state, typeSpecifierNamestate) %}
    {%- set typeSpecifierNamestate.print = TypeSpecifierPrint %}
{%- endmacro %}

{%- macro TypeSpecifierClassInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set TypeSpecifierClass = namespace() %}
    {%- set TypeSpecifierClass.construct = TypeSpecifierClassConstruct %}
    {%- set environment.TypeSpecifierClass = TypeSpecifierClass %}
{%- endmacro %}