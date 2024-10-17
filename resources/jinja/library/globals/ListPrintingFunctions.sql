{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}

{#- Prints items from a list, delimiting them with a joiner #}
{%- macro printItemsFromList(environment, listOfItemsToPrint, joiner) %}
    {%- set ns = namespace(first = true) %}
    {%- for item in listOfItemsToPrint %}
        {%- if ns.first %}
            {%- set ns.first = false %}
        {%- else -%}
            {{ joiner }}
        {%- endif -%}
        {{ item }}
    {%- endfor %}
{%- endmacro %}

{#- Prints operators from a list, delimiting them with a joiner #}
{%- macro printOperatorsFromList(environment, state, listOfArgumentsToPrint, joiner) %}
    {%- set ns = namespace(first = true) %}
    {%- for item in listOfArgumentsToPrint %}
        {%- if ns.first -%}
            {%- set ns.first = false %}
        {%- else -%}
            {{ joiner }}
        {%- endif -%}
        {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, item) }}
    {%- endfor %}
{%- endmacro %}

{#- Prints operators from a list, wrapping them as a single-value table if they are simple #}
{%- macro printOperatorsFromListWrappingSimpleItems(environment, state, listOfArgumentsToPrint, joiner) %}
    {%- set ns = namespace(first = true) %}
    {%- for item in listOfArgumentsToPrint %}
        {%- if ns.first -%}
            {%- set ns.first = false %}
        {%- else -%}
            {{ joiner }}
        {%- endif -%}

        {#- coerce item #}
        {%- set carrier = namespace() %}
        {%- do item['operator'].getDataType(environment, item['operator'], carrier, state, item) -%}
        {%- if carrier.value == environment.DataTypeEnum.SIMPLE -%}
            {{ environment.wrapAsSingleValueTable(environment.OperatorHandler.print(environment, environment.OperatorHandler, state, item)) }}
        {%- else -%}
            {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, item) }}
        {%- endif -%}
    {%- endfor %}
{%- endmacro %}

{%- macro ListPrintingFunctionsInit(environment) -%}
    {#- initialize prerequisites #}
    {%- do DataTypeEnumInit(environment) %}
    {%- do StandardFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set environment.printItemsFromList = printItemsFromList %}
    {%- set environment.printOperatorsFromList = printOperatorsFromList %}
    {%- set environment.printOperatorsFromListWrappingSimpleItems = printOperatorsFromListWrappingSimpleItems %}
{%- endmacro %}