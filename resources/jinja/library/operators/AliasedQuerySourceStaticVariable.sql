{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro AliasedQuerySourcePrint(environment, this, state, arguments) -%}
    ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}) AS {{ arguments['alias'] }}
{%- endmacro %}

{%- macro AliasedQuerySourceStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set AliasedQuerySource = namespace() %}
    {%- set environment.AliasedQuerySource = AliasedQuerySource %}
    {%- do environment.OperatorClass.construct(environment, none, environment.AliasedQuerySource) %}
    {%- set AliasedQuerySource.print = AliasedQuerySourcePrint %}
{%- endmacro %}