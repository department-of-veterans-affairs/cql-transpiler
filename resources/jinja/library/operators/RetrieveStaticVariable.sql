{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro RetrievePrint(environment, this, state, arguments) -%}
    {%- if arguments['resultTypeLabel'] %}
        {%- set resultTypeLabel = arguments['resultTypeLabel'] %}
    {%- else %}
        {%- set resultTypeLabel = arguments['templateId'] %}
    {%- endif -%}
    {{ "{{" }} transpilerRetrieve({% if arguments['valueSet'] %}{{ "'" }}{{ arguments['valueSet']['operator'].print(environment, arguments['valueSet']['operator'], state, arguments['valueSet']) }}{{ "'" }}{% else %}none{% endif %}, {{ "'" }}{{ arguments['modelType'].split(':')[2]|lower }}{{ "'" }}, {{ "'" }}{{ resultTypeLabel|lower|replace(" ", "_")|replace(",", "") }}{{ "'" }}, {{ "'" }}{{ arguments['modelType'].split(':')[3]|replace("_", "") }}{{ "'" }}, {{ "'" }}{{ arguments['codeProperty'] }}{{ "'" }}) {{ "}}"}}
{%- endmacro %}

{%- macro RetrieveStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Retrieve = namespace() %}
    {%- set environment.Retrieve = Retrieve %}
    {%- do environment.OperatorClass.construct(environment, none, Retrieve) %}
    {%- set Retrieve.defaultDataState = environment.DataStateEnum.TABLE %}
    {%- set Retrieve.defaultDataSQLFormat = environment.DataSQLFormatEnum.QUERY %}
    {%- set Retrieve.defaultAccessType = environment.AccessTypeEnum.SELECT_FROM %}
    {%- set Retrieve.print = RetrievePrint %}
{%- endmacro %}