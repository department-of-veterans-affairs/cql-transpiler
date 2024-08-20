{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}

{%- macro printUnimplemented(environment, this, state, arguments) -%}
    {%- set previousInsideSqlComment = state.insideSqlComment -%}
    {%- set state.insideSqlComment = true -%}
    {%- if not previousInsideSqlComment %}/* {% endif -%}
    Unimplemented Operator: {{ arguments['operator'] }} with arguments: {{arguments}} with children: [*/{{ environment.printOperatorsFromList(state, arguments['children'], ", ") }}/*]
    {%- if not previousInsideSqlComment %} */{% endif %}
    {%- set state.insideSqlComment = previousInsideSqlComment -%}
{%- endmacro %}

{%- macro UnimplementedOperatorPrintFunctions(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set environment.printUnimplemented = printUnimplemented %}
{%- endmacro %}
