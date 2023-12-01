{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro UnsupportedPrint(environment, this, state, arguments) %}
    {{ environment.logError(environment, "TODO: unsupported operator "~arguments['unsupportedOperator']) }}
{%- endmacro %}

{%- macro UnsupportedStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Unsupported = namespace() %}
    {%- set environment.Unsupported = Unsupported %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Unsupported) %}
    {%- set Unsupported.print = UnsupportedPrint %}
{%- endmacro %}