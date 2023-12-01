{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ConversionHandlingFunctions.sql" import ConversionHandlingFunctionsInit %}

{%- macro PropertyGetDataSQLFormat(environment, this, state, arguments, carrier) %}
    {%- if arguments['child'] %}
        {#- Properties not accessible via the dot operator get turned into single value tables #}
        {%- set accessTypeCarrier = namespace() %}
        {%- do arguments['child']['operator'].getAccessType(environment, arguments['child']['operator'], state, arguments['child'], accessTypeCarrier) %}
        {%- if accessTypeCarrier.value == environment.AccessTypeEnum.SELECT_FROM %}
            {%- set carrier.value = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE %}
        {%- else %}
            {%- set carrier.value = environment.DataSQLFormatEnum.RAW_VALUE %}
        {%- endif %}
    {%- else %}
        {%- set carrier.value = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- endif %}
{%- endmacro %}

{%- macro PropertyPrint(environment, this, state, arguments) -%}
    {%- if arguments['child'] -%}
        {%- set accessTypeCarrier = namespace() %}
        {%- do arguments['child']['operator'].getAccessType(environment, arguments['child']['operator'], state, arguments['child'], accessTypeCarrier) %}
        {%- if accessTypeCarrier.value == environment.AccessTypeEnum.DOT_PROPERTY -%}
            {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}.{{ arguments['path'] }}
        {%- elif accessTypeCarrier.value == environment.AccessTypeEnum.SCALAR_STRUCT -%}
            ({{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}).{{ arguments['path'] }}
        {%- elif accessTypeCarrier.value == environment.AccessTypeEnum.SELECT_FROM -%}
            SELECT {{ arguments['path'] }} FROM {{ environment.coerceAndReformat(environment, state, arguments['child']['operator'], arguments['child'], environment.DataStateEnum.TABLE, environment.DataSQLFormatEnum.QUERY, true, namespace(), namespace()) }}
        {%- else %}
            {{ 0/0 }}
        {%- endif %}
    {%- elif arguments['path'] != none -%}
        {{ arguments['scope'] }}.{{ arguments['path'] }}
    {%- else -%}
        {{ arguments['scope'] }}
    {%- endif %}
{%- endmacro %}

{%- macro PropertyStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do ConversionHandlingFunctionsInit(environment) %}
    {# initialize member variables #}
    {%- set Property = namespace() %}
    {%- set environment.Property = Property %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Property) %}
    {%- set Property.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Property.defaultAccessType = environment.AccessTypeEnum.DOT_PROPERTY %}
    {%- set Property.getDataSQLFormat = PropertyGetDataSQLFormat %}
    {%- set Property.print = PropertyPrint %}
{%- endmacro %}