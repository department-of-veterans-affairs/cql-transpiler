{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ConversionHandlingFunctions.sql" import ConversionHandlingFunctionsInit %}

{%- macro CoalesceGetDataSQLFormat(environment, this, state, arguments, carrier) %}
    {%- set carrier.value = this.defaultDataSQLFormat %}
    {%- set dataStateCarrier = namespace() %}
    {%- do this.getDataState(environment, this, state, arguments, dataStateCarrier) %}
    {%- if dataStateCarrier.value == environment.DataStateEnum.TABLE %}
        {%- set carrier.value = environment.DataSQLFormatEnum.QUERY_REFERENCE %}
    {%- elif dataStateCarrier.value == environment.DataStateEnum.ENCAPSULATED %}
        {%- set carrier.value = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE_REFERENCE %}
    {%- elif (dataStateCarrier.value == environment.DataStateEnum.SIMPLE) or (dataStateCarrier.value == environment.DataStateEnum.NULL) %}
        {%- set carrier.value = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- endif %}
{%- endmacro %}

{%- macro CoalescePrint(environment, this, state, arguments) -%}
    {%- set dataSQLFormatCarrier = namespace() -%}
    {%- do this.getDataSQLFormat(environment, this, state, arguments, dataSQLFormatCarrier) %}
    {%- set dataStateCarrier = namespace() -%}
    {%- do this.getDataState(environment, this, state, arguments, dataStateCarrier) %}

    {%- if dataSQLFormatCarrier.value == environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE -%}
        {{ environment.wrapAsSingleValueTable(
            environment,
            "coalesce("
                ~environment.printOperatorsFromListCoercing(environment, state, arguments['children'], ', ', dataStateCarrier.value, true)
                ~")"
        ) }}
    {%- else -%}
        coalesce({{ environment.printOperatorsFromListCoercing(environment, state, arguments['children'], ', ', dataStateCarrier.value, true) }})
    {%- endif %}
{%- endmacro %}

{%- macro CoalesceStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do ConversionHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set Coalesce = namespace() %}
    {%- set environment.Coalesce = Coalesce %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Coalesce) %}
    {%- set Coalesce.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set Coalesce.getDataSQLFormat = CoalesceGetDataSQLFormat %}
    {%- set Coalesce.print = CoalescePrint %}
{%- endmacro %}