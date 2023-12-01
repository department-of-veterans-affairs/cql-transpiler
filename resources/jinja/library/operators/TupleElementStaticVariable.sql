{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ConversionHandlingFunctions.sql" import ConversionHandlingFunctionsInit %}

{%- macro TupleElementGetDataState(environment, this, state, arguments, carrier) %}
    {%- set dataStateCarrier = namespace() %}
    {%- do arguments['child']['operator'].getDataState(environment, arguments['child']['operator'], dataStateCarrier, state, arguments['child']) -%}
    {%- if dataStateCarrier.value == environment.DataSQLFormatEnum.SIMPLE %}
        {%- set carrier.value = environment.DataSQLFormatEnum.QUERY %}
    {%- elif dataStateCarrier.value == environment.DataSQLFormatEnum.ENCAPSULATED or dataStateCarrier.value == environment.DataSQLFormatEnum.TABLE -%}
        {%- set carrier.value = environment.DataSQLFormatEnum.ENCAPSULATED %}
    {%- endif %}
{%- endmacro %}

{%- macro TupleElementPrint(environment, this, state, arguments) -%}
    {{ environment.logError(environment, "TODO: TupleElement") }}
{%- endmacro %}

{%- macro TupleElementStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do ConversionHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set TupleElement = namespace() %}
    {%- set environment.TupleElement = TupleElement %}
    {%- do environment.OperatorClass.construct(environment, none, environment.TupleElement) %}
    {%- set TupleElement.defaultDataSQLFormat = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE %}
    {%- set TupleElement.getDataState = TupleElementGetDataState %}
    {%- set TupleElement.print = TupleElementPrint %}
{%- endmacro %}