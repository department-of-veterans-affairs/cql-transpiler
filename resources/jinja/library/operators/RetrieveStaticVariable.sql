{#-
    Environment prerequisites:
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro RetrievePrint(environment, this, state, arguments) -%}
    {%- if not previousInsideSqlComment -%}
        /*
    {%- endif -%}
    <Unsupported Retrieve with arguments <modeltype: {{ arguments['modelType'] }}, templateId: {{ arguments['templateId'] }}>
    {%- if not previousInsideSqlComment -%}
        */
    {%- endif %}
{%- endmacro %}

{%- macro RetrieveStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Retrieve = namespace() %}
    {%- do environment.OperatorClass.construct(environment, none, Retrieve) %}
    {%- set Retrieve.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set Retrieve.print = RetrievePrint %}
    {%- set environment.Retrieve = Retrieve %}
{%- endmacro %}