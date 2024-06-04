{#    
    Environment prerequisites:
        * ListPrintingFunctions.sql
        * OperatorClass.sql
#}
{% from "jinja_transpilation_libraries/sparksql/default/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "jinja_transpilation_libraries/sparksql/default/globals/ListPrintingFunctions.sql" import ListPrintingFunctionsInit %}

{% macro printDefault(environment, this, state, arguments) %}
{%-     set previousInsideSqlComment = state.insideSqlComment %}
{%-     set state.insideSqlComment = true %}
{%-     if not previousInsideSqlComment -%}
            /*
{%-     endif -%}
        Unsupported Operator: <
{%-     if arguments['unsupportedOperator'] != none or arguments['unsupportedOperator']|length == 0 -%}
            {{ arguments['unsupportedOperator'] }}
{%-     else -%}
            {{ arguments }}
{%      endif -%}
        > with arguments: <{{arguments}}> with children: <[{{ environment.printOperatorsFromList(state, arguments['children'], ", ") }}]>
{%-     if not previousInsideSqlComment -%}
            */
{%-     endif %}
{%-     set state.insideSqlComment = previousInsideSqlComment %}
{%- endmacro %}

{% macro UnsupportedOperatorConstruct(environment, state, unsupportedOperatorNamespace) %}
{%-     do environment.OperatorClass.construct(environment, state, unsupportedOperatorNamespace) %}
{%-     set unsupportedOperatorNamespace.print = printDefault %}
{%- endmacro %}

{% macro UnsupportedOperatorClassInit(environment) %}
{# initialize prerequisites #}
{%-     do OperatorClassInit(environment) %}
{%-     do ListPrintingFunctionsInit(environment) %}
{# initialize member variables #}
{%-     set UnsupportedOperatorClass = namespace() %}
{%-     set UnsupportedOperatorClass.construct = UnsupportedOperatorConstruct %}
{%-     set environment.UnsupportedOperatorClass = UnsupportedOperatorClass %}
{% endmacro %}
