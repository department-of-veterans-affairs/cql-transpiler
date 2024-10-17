{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro AsPrint(environment, this, state, arguments) -%}
    {#- arguments['typeSpecifier'] is unused -#}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child'])}}
{%- endmacro %}

{%- macro AsStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set As = namespace() %}
    {%- set environment.As = As %}
    {%- do environment.OperatorClass.construct(environment, none, environment.As) %}
    {%- set As.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set As.print = AsPrint %}
{%- endmacro %}