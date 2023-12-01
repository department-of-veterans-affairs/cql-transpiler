{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}

{%- macro printUnimplemented(environment, this, state, arguments) -%}
    {{ environment.logError(environment, "Unimplemented operator "~arguments['operator']~" with arguments "~arguments) }}
{%- endmacro %}

{%- macro UnimplementedOperatorPrintFunctions(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set environment.printUnimplemented = printUnimplemented %}
{%- endmacro %}
