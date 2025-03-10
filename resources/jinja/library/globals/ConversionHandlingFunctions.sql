{%- from "library/globals/DataSQLFormatEnum.sql" import DataSQLFormatEnumInit %}
{%- from "library/globals/DataStateEnum.sql" import DataStateEnumInit %}
{%- from "library/globals/StateConversionFunctions.sql" import StateConversionFunctionsInit %}

{%- macro justCoerce(environment, state, operator, arguments, originalDataState, targetDataState, isWrappable, wrapSubqueriesInParentheses, carrierForFinalDataState, carrierForFinalDataFormat) -%}
    {%- if (originalDataState == targetDataState) or (originalDataState == environment.DataStateEnum.NULL) -%}
        {#- no need to coerce -#}
        {{ "(" if isWrappable and wrapSubqueriesInParentheses else "" }}{{ operator.print(environment, operator, state, arguments) }}{{ ")" if isWrappable and wrapSubqueriesInParentheses else "" }}
    {%- else -%}
        {%- if targetDataState == environment.DataStateEnum.ENCAPSULATED and originalDataState == environment.DataStateEnum.TABLE -%}
            {{ "(" if wrapSubqueriesInParentheses else "" }}{{ environment.collect(environment, state.context, operator.print(environment, operator, state, arguments)) }}{{ ")" if wrapSubqueriesInParentheses else "" }}
            {%- set carrierForFinalDataState.value = environment.DataStateEnum.ENCAPSULATED -%}
            {%- set carrierForFinalDataFormat.value = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE -%}
        {%- elif targetDataState == environment.DataStateEnum.TABLE and originalDataState == environment.DataStateEnum.ENCAPSULATED -%}
            {{ "(" if wrapSubqueriesInParentheses else "" }}{{ environment.decollect(environment, state.context, operator.print(environment, operator, state, arguments)) }}{{ ")" if wrapSubqueriesInParentheses else "" }}
            {%- set carrierForFinalDataState.value = environment.DataStateEnum.TABLE -%}
            {%- set carrierForFinalDataFormat.value = environment.DataSQLFormatEnum.QUERY -%}
        {%- elif targetDataState == environment.DataStateEnum.TABLE and originalDataState == environment.DataStateEnum.SIMPLE -%}
            {{ environment.logWarning(environment, "coercing simple value to table -- assuming simple value is a reference to an encapsulated table") }}{#- -#}
            {{ "(" if wrapSubqueriesInParentheses else "" }}{{ environment.decollect(environment, state.context, operator.print(environment, operator, state, arguments)) }}{{ ")" if wrapSubqueriesInParentheses else "" }}
            {%- set carrierForFinalDataState.value = environment.DataStateEnum.TABLE -%}
            {%- set carrierForFinalDataFormat.value = environment.DataSQLFormatEnum.QUERY -%}
        {%- else -%}
            {{ environment.logError(environment, "cannot coerce data of state "~originalDataState~" to state "~targetDataState~". Printing subordinate data anyways.") }}{#- -#}
            {{ "(" if isWrappable and wrapSubqueriesInParentheses else "" }}{{ operator.print(environment, operator, state, arguments) }}{{ ")" if isWrappable and wrapSubqueriesInParentheses else "" }}
        {%- endif %}
    {%- endif -%}
{%- endmacro %}

{%- macro justReformat(environment, state, operator, arguments, originalDataFormat, targetDataFormat, isWrappable, wrapSubqueriesInParentheses, carrierForFinalDataFormat) -%}
    {%- if originalDataFormat == targetDataFormat -%}
        {#- no need to reformat -#}
        {{ "(" if isWrappable and wrapSubqueriesInParentheses else "" }}{{ operator.print(environment, operator, state, arguments) }}{{ ")" if isWrappable and wrapSubqueriesInParentheses else "" }}
    {%- else -%}
        {%- if originalDataFormat == environment.DataSQLFormatEnum.RAW_VALUE and targetDataFormat == environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE -%}
            {{ "(" if wrapSubqueriesInParentheses else "" }}{{ environment.wrapAsSingleValueTable(environment, operator.print(environment, operator, state, arguments)) }}{{ ")" if wrapSubqueriesInParentheses else "" }}
            {%- set carrierForFinalDataFormat.value = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE %}
        {%- elif originalDataFormat == environment.DataSQLFormatEnum.QUERY_REFERENCE and targetDataFormat == environment.DataSQLFormatEnum.QUERY -%}
            {{ "(" if isWrappable and wrapSubqueriesInParentheses else "" }}SELECT * FROM {{ operator.print(environment, operator, state, arguments) }}{{ ")" if isWrappable and wrapSubqueriesInParentheses else "" }}
        {%- elif originalDataFormat == environment.DataSQLFormatEnum.RAW_VALUE and targetDataFormat == environment.DataSQLFormatEnum.QUERY -%}
            {{ "(" if isWrappable and wrapSubqueriesInParentheses else "" }}SELECT * FROM {{ operator.print(environment, operator, state, arguments) }}{{ ")" if isWrappable and wrapSubqueriesInParentheses else "" }}
        {%- else -%}
            {{ 0/0 }}
            {{ environment.logError(environment, "cannot reformat data of format "~originalDataFormat~" to format "~targetDataFormat~". Printing subordinate data anyways.") }}{#- -#}
            {{ "(" if isWrappable and wrapSubqueriesInParentheses else "" }}{{ operator.print(environment, operator, state, arguments) }}{{ ")" if isWrappable and wrapSubqueriesInParentheses else "" }}
        {%- endif %}
    {%- endif -%}
{%- endmacro %}

{%- macro coerceAndReformat(environment, state, operator, arguments, targetDataState, targetDataFormat, wrapSubqueriesInParentheses, carrierForFinalDataState, carrierForFinalDataFormat) -%}
    {#- aquire the original data state and format #}
    {%- set originalDataStateCarrier = namespace() %}
    {%- do operator.getDataState(environment, operator, state, arguments, originalDataStateCarrier) %}
    {%- set originalDataSQLFormatCarrier = namespace() %}
    {%- do operator.getDataSQLFormat(environment, operator, state, arguments, originalDataSQLFormatCarrier) %}

    {#- by default, no changes to the data state and format are made #}
    {%- set carrierForFinalDataState.value = originalDataStateCarrier.value -%}
    {%- set carrierForFinalDataFormat.value = originalDataSQLFormatCarrier.value -%}

    {#- check to see if the original operator is a query that might need to be wrapped #}
    {%- if originalDataSQLFormatCarrier.value in [environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE, environment.DataSQLFormatEnum.QUERY] %}
        {%- set isWrappable = true %}
    {%- endif %}

    {%- if (targetDataState == none) and (targetDataFormat == none) -%}
        {#- only query parenentheses wrapping is necessary -#}
        {{ "(" if isWrappable and wrapSubqueriesInParentheses else "" }}{{ operator.print(environment, operator, state, arguments) }}{{ ")" if isWrappable and wrapSubqueriesInParentheses else "" }}
    {%- elif targetDataState == none -%}
        {#- only reformatting is necessary -#}
        {{ justReformat(environment, state, operator, arguments, originalDataSQLFormatCarrier.value, targetDataFormat, isWrappable, wrapSubqueriesInParentheses, carrierForFinalDataFormat) }}
    {%- elif targetDataFormat == none -%}
        {#- only coercion is necessary -#}
        {{ justCoerce(environment, state, operator, arguments, originalDataStateCarrier.value, targetDataState, isWrappable, wrapSubqueriesInParentheses, carrierForFinalDataState, carrierForFinalDataFormat) }}
    {%- else -%}
        {%- if targetDataFormat == environment.DataSQLFormatEnum.QUERY and targetDataState == environment.DataStateEnum.TABLE -%}
            {%- if originalDataStateCarrier.value == environment.DataStateEnum.TABLE -%}
                {%- if originalDataSQLFormatCarrier.value == environment.DataSQLFormatEnum.QUERY -%}
                    {{ "(" if wrapSubqueriesInParentheses else "" }}{{ operator.print(environment, operator, state, arguments) }}{{ ")" if wrapSubqueriesInParentheses else "" }}
                {%- elif originalDataSQLFormatCarrier.value == environment.DataSQLFormatEnum.QUERY_REFERENCE -%}
                    {{ "(" if wrapSubqueriesInParentheses else "" }}SELECT * FROM {{ operator.print(environment, operator, state, arguments) }}{{ ")" if wrapSubqueriesInParentheses else "" }}
                {%- else -%}
                    {{ 0/0 }}
                {%- endif %}
            {%- elif originalDataStateCarrier.value == environment.DataStateEnum.SIMPLE -%}
                {{ environment.logWarning(environment, "speculative coercion follows -- SIMPLE to QUERY/TABLE") }}{#- -#}
                {{ "(" if wrapSubqueriesInParentheses else "" }}{{ environment.decollect(environment, state.context, operator.print(environment, operator, state, arguments)) }}{{ ")" if wrapSubqueriesInParentheses else "" }}
            {%- elif (originalDataStateCarrier.value == environment.DataStateEnum.ENCAPSULATED) and (originalDataSQLFormatCarrier.value == environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE) -%}
                {{ "(" if wrapSubqueriesInParentheses else "" }}{{ environment.decollect(environment, state.context, operator.print(environment, operator, state, arguments)) }}{{ ")" if wrapSubqueriesInParentheses else "" }}
            {%- else -%}
                {{ 0/0 }}
            {%- endif -%}
            {%- set carrierForFinalDataState.value = environment.DataStateEnum.TABLE -%}
            {%- set carrierForFinalDataFormat.value = environment.DataSQLFormatEnum.QUERY -%}
        {%- else -%}
            {{ environment.logError(environment, "TODO: support coerce + reformat "~targetDataFormat~"+"~targetDataState~". Printing subordinate data anyways.") }}{#- -#}
            {{ "(" if isWrappable and wrapSubqueriesInParentheses else "" }}{{ operator.print(environment, operator, state, arguments) }}{{ ")" if isWrappable and wrapSubqueriesInParentheses else "" }}
        {%- endif %}
    {%- endif %}
{%- endmacro %}

{%- macro printOperatorsFromListCoercing(environment, state, listOfArgumentsToPrint, joiner, coerceTo, wrapSubqueriesInParentheses) %}
    {%- set ns = namespace(first = true) %}
    {%- for item in listOfArgumentsToPrint %}
        {%- if ns.first -%}
            {%- set ns.first = false %}
        {%- else -%}
            {{ joiner }}
        {%- endif -%}
        {{ coerceAndReformat(environment, state, item['operator'], item, coerceTo, none, wrapSubqueriesInParentheses, namespace(), namespace()) }}
    {%- endfor %}
{%- endmacro %}

{%- macro ConversionHandlingFunctionsInit(environment) -%}
    {#- initialize prerequisites #}
    {%- do DataSQLFormatEnumInit(environment) %}
    {%- do DataStateEnumInit(environment) %}
    {%- do StateConversionFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set environment.coerceAndReformat = coerceAndReformat %}
    {%- set environment.printOperatorsFromListCoercing = printOperatorsFromListCoercing %}
{%- endmacro %}
