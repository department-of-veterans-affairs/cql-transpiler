{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro AsPrint(environment, this, state, arguments) -%}
    {#- arguments['typeSpecifier'] is unused -#}
    {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}
{%- endmacro %}

{%- macro AsStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set As = namespace() %}
    {%- set environment.As = As %}
    {%- do environment.OperatorClass.construct(environment, none, environment.As) %}
    {%- set As.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set As.defaultDataSQLFormat = environment.DataSQLFormatEnum.INHERITED %}
    {%- set As.defaultAccessType = environment.AccessTypeEnum.INHERITED %}
    {%- set As.print = AsPrint %}
{%- endmacro %}