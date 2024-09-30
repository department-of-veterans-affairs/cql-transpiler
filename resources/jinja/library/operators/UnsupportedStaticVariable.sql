{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
        * ListPrintingFunctions.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/ListPrintingFunctions.sql" import ListPrintingFunctionsInit %}

{%- macro UnsupportedPrint(environment, this, state, arguments) %}
    {%- set previousInsideSqlComment = state.insideSqlComment %}
    {%- set state.insideSqlComment = true %}
    {%- if not previousInsideSqlComment -%}
        /* Unsupported Operator: {{ arguments['unsupportedOperator'] }} with arguments <{{ arguments }}> */
    {%- endif %}
    {%- set state.insideSqlComment = previousInsideSqlComment %}
{%- endmacro %}


{%- macro UnsupportedStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {%- do ListPrintingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set Unsupported = namespace() %}
    {%- set environment.Unsupported = Unsupported %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Unsupported) %}
    {%- set Unsupported.defaultDataType = environment.DataTypeEnum.UNKNOWN %}
    {%- set Unsupported.print = UnsupportedPrint %}
{%- endmacro %}