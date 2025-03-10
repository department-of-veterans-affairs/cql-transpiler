{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro QuantityPrint(environment, this, state, arguments) -%}
    {%- if arguments['unit'] == 'year' -%}
        INTERVAL {{ arguments['value'] }} YEAR
    {%- elif arguments['unit'] == 'month' -%}
        INTERVAL {{ arguments['value'] }} MONTH
    {%- elif arguments['unit'] == 'day' or arguments['unit'] == 'days' -%}
        INTERVAL {{ arguments['value'] }} DAY
    {%- elif arguments['unit'] == 'hour' -%}
        INTERVAL {{ arguments['value'] }} HOUR
    {%- elif arguments['unit'] == 'minute' -%}
        INTERVAL {{ arguments['value'] }} MINUTE
    {%- elif arguments['unit'] == 'second' -%}
        INTERVAL {{ arguments['value'] }} SECOND
    {%- elif arguments['unit'] == 'millisecond' -%}
        INTERVAL {{ arguments['value'] }} MILLISECOND
    {%- else -%}
        {{ environment.logError(environment, "TODO: InValQuantity -- support unit::value "~arguments['unit']~"::"~arguments['value']) }}
    {%- endif %}
{%- endmacro %}

{%- macro QuantityStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Quantity = namespace() %}
    {%- set environment.Quantity = Quantity %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Quantity) %}
    {%- set Quantity.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Quantity.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Quantity.print = QuantityPrint %}
{%- endmacro %}