{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro MaxValuePrint(environment, this, state, arguments) -%}
    {{ environment.logError(environment, "TODO: MaxValue") }}
{%- endmacro %}

{%- macro MaxValueStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set MaxValue = namespace() %}
    {%- set environment.MaxValue = MaxValue %}
    {%- do environment.OperatorClass.construct(environment, none, environment.MaxValue) %}
    {%- set MaxValue.print = MaxValuePrint %}
{%- endmacro %}