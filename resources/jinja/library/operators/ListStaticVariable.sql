{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ListPrint(environment, this, state, arguments) -%}
    SELECT collect_list({{ environment.printSingleValueColumnName(environment) }}) FROM (
    {%- if arguments['children']|length == 0 -%}
        {{ environment.printEmptyTable(environment) }}
    {%- else -%}
        {{ environment.logError(environment, "TODO: list") }}
    {%- endif -%}
    )
{%- endmacro %}

{%- macro ListStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardFunctionsInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set List = namespace() %}
    {%- set environment.List = List %}
    {%- do environment.OperatorClass.construct(environment, none, environment.List) %}
    {%- set List.defaultDataSQLFormat = environment.DataSQLFormatEnum.QUERY %}
    {%- set List.defaultDataState = environment.DataStateEnum.TABLE %}
    {%- set List.defaultAccessType = environment.AccessTypeEnum.SELECT_FROM %}
    {%- set List.print = ListPrint %}
{%- endmacro %}