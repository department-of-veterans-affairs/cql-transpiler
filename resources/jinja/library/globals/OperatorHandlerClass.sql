{%- from "library/globals/StateClass.sql" import StateClassInit %}
{%- from "library/globals/TypeConversionFunctions.sql" import TypeConversionFunctionsInit %}

{%- macro OperatorHandlerPrint(environment, this, state, arguments) %}
    {%- if state == none %}
        {%- set state = namespace() %}
        {%- do environment.StateClass.construct(environment, state) %}
    {%- endif %}
    {%- set operatorDataTypeEnumCarrier = namespace() %}
    {%- do arguments['operator'].getDataType(environment, arguments['operator'], operatorDataTypeEnumCarrier, state, arguments) -%}
    {{ environment.coerce(environment, operatorDataTypeEnumCarrier.value, state.coercionInstructions[operatorDataTypeEnumCarrier.value], state, arguments) }}
{%- endmacro %}

{%- macro OperatorHandlerConstruct(environment, operatorHandlerNamespace) %}
    {%- set operatorHandlerNamespace.print = OperatorHandlerPrint %}
{%- endmacro %}

{%- macro OperatorHandlerClassInit(environment) -%}
    {#-  initialize prerequisites #}
    {%- do StateClassInit(environment) %}
    {%- do TypeConversionFunctionsInit(environment) %}
    {#-  initialize OperatorHandlerClass #}
    {%- set OperatorHandlerClass = namespace() %}
    {%- set OperatorHandlerClass.construct = OperatorHandlerConstruct %}
    {%- set environment.OperatorHandlerClass = OperatorHandlerClass %}
{%- endmacro %}