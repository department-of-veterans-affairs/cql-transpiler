{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro NotPrint(environment, this, state, arguments) -%}
    NOT {{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }}
{%- endmacro %}

{%- macro NotStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Not = namespace() %}
    {%- set environment.Not = Not %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Not) %}
    {%- set Not.print = NotPrint %}
{%- endmacro %}