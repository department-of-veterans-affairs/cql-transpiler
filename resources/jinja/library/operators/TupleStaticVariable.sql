{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro TuplePrint(environment, this, state, arguments) -%}
    {{ environment.logError(environment, "TODO: Tuple") }}
{%- endmacro %}

{%- macro TupleStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Tuple = namespace() %}
    {%- set environment.Tuple = Tuple %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Tuple) %}
    {%- set Tuple.print = TuplePrint %}
{%- endmacro %}