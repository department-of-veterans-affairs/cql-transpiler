{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro FirstPrint(environment, this, state, arguments) -%}
    {%- if arguments['orderBy'] -%}
        /* todo -- First -- support orderBy */
    {%- else -%}
        first_value({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
    {%- endif -%}
{%- endmacro %}

{%- macro FirstStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set First = namespace() %}
    {%- set environment.First = First %}
    {%- do environment.OperatorClass.construct(environment, none, environment.First) %}
    {%- set First.allowsSelectFromAccessTypeByDefault = true %}
    {%- set First.print = FirstPrint %}
{%- endmacro %}