{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro IdentifierRefPrint(environment, this, state, arguments) -%}
    {{ arguments['name'] }}
{%- endmacro %}

{%- macro IdentifierRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set IdentifierRef = namespace() %}
    {%- set environment.IdentifierRef = IdentifierRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.IdentifierRef) %}
    {%- set IdentifierRef.print = IdentifierRefPrint %}
{%- endmacro %}