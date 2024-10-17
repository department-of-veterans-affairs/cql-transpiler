{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro RetrievePrint(environment, this, state, arguments) -%}
    /* todo -- Retrieve -- modeltype: {{ arguments['modelType'] }}, templateId: {{ arguments['templateId'] }} */
{%- endmacro %}

{%- macro RetrieveStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Retrieve = namespace() %}
    {%- set environment.Retrieve = Retrieve %}
    {%- do environment.OperatorClass.construct(environment, none, Retrieve) %}
    {%- set Retrieve.allowsSelectFromAccessTypeByDefault = true %}
    {%- set Retrieve.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set Retrieve.print = RetrievePrint %}
{%- endmacro %}