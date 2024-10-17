{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro CountPrint(environment, this, state, arguments) -%}
    count({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
{%- endmacro %}

{%- macro CountStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Count = namespace() %}
    {%- set environment.Count = Count %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Count) %}
    {%- set Count.print = CountPrint %}
{%- endmacro %}