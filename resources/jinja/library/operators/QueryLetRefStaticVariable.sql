{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro QueryLetRefPrint(environment, this, state, arguments) -%}
    {{ arguments['referencedName'] }}
{%- endmacro %}

{%- macro QueryLetRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set QueryLetRef = namespace() %}
    {%- set environment.QueryLetRef = QueryLetRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.QueryLetRef) %}
    {%- set QueryLetRef.print = QueryLetRefPrint %}
{%- endmacro %}