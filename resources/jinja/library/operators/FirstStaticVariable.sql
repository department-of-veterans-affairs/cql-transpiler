{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro FirstPrint(environment, this, state, arguments) -%}
    {%- if arguments['orderBy'] -%}
        /* unsupported argument in 'First' orderBy:<{{ arguments['orderBy'] }}>*/
    {%- endif -%}
    first_value({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
{%- endmacro %}

{%- macro FirstStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set First = namespace() %}
    {%- set environment.First = First %}
    {%- do environment.OperatorClass.construct(environment, none, environment.First) %}
    {%- set First.print = FirstPrint %}
{%- endmacro %}