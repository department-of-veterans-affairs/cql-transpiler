{# Prints items from a list, delimiting them with a joiner #}
{%- macro printItemsFromList(environment, listOfItemsToPrint, joiner) %}
{%-     set ns = namespace(first = true) %}
{%-     for item in listOfItemsToPrint %}
{%-         if ns.first %}
{%-             set ns.first = false %}
{%-         else %}
{{ joiner }}
{%-         endif %}
{{ item }}
{%-     endfor %}
{%- endmacro %}

{# Prints operators from a list, delimiting them with a joiner #}
{%- macro printOperatorsFromList(environment, state, listOfArgumentsToPrint, joiner) %}
{%-     set ns = namespace(first = true) %}
{%-     for item in listOfArgumentsToPrint %}
{%-         if ns.first %}
{%-             set ns.first = false %}
{%-         else %}
{{ joiner }}
{%-         endif %}
{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, item) }}
{%-     endfor %}
{%- endmacro %}

{% macro ListPrintingFunctionsInit(environment) -%}
{%-     set environment.printItemsFromList = printItemsFromList %}
{%-     set environment.printOperatorsFromList = printOperatorsFromList %}
{% endmacro %}