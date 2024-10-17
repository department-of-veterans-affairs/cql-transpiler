{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro InValueSetPrint(environment, this, state, arguments) -%}
    {%- if arguments['valueSetExpression'] -%}
        {%- set valueSet = arguments['valueSetExpression'] %}
    {%- else -%}
        {%- set valueSet = arguments['valueSetReference'] %}
    {%- endif -%}
    /* todo -- InValueSet -- check for element: <{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}>, valueset <{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, valueSet) }}> */
{%- endmacro %}

{%- macro InValueSetStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set InValueSet = namespace() %}
    {%- set environment.InValueSet = InValueSet %}
    {%- do environment.OperatorClass.construct(environment, none, environment.InValueSet) %}
    {%- set InValueSet.print = InValueSetPrint %}
{%- endmacro %}