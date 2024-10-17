{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro LastPrint(environment, this, state, arguments) -%}
    {%- if arguments['orderBy'] -%}
        /* todo -- Last -- support orderBy */
    {%- else -%}
        last_value({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
    {%- endif -%}

{%- endmacro %}

{%- macro LastStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Last = namespace() %}
    {%- set environment.Last = Last %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Last) %}
    {%- set Last.allowsSelectFromAccessTypeByDefault = true %}
    {%- set Last.print = LastPrint %}
{%- endmacro %}