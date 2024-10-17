{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ToDecimalPrint(environment, this, state, arguments) -%}
    (0.0 + {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
{%- endmacro %}

{%- macro ToDecimalStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ToDecimal = namespace() %}
    {%- set environment.ToDecimal = ToDecimal %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ToDecimal) %}
    {%- set ToDecimal.print = ToDecimalPrint %}
{%- endmacro %}