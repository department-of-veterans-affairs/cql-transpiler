{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro InValueSetPrint(environment, this, state, arguments) -%}
    {%- if not previousInsideSqlComment -%}
        /*
    {%- endif -%}
    <Unsupported InValueSet>
    {%- if not previousInsideSqlComment -%}
        */
    {%- endif %}
{%- endmacro %}

{%- macro InValueSetStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set InValueSet = namespace() %}
    {%- set environment.InValueSet = InValueSet %}
    {%- do environment.OperatorClass.construct(environment, none, environment.InValueSet) %}
    {%- set InValueSet.print = InValueSetPrint %}
{%- endmacro %}