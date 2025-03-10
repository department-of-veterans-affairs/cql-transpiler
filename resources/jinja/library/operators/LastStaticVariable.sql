{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro LastPrint(environment, this, state, arguments) -%}
    {%- if arguments['orderBy'] -%}
        {{ environment.logError(environment, "TODO: support Last orderBy") }}
    {%- else -%}
        {#- find data state of child #}
        {%- set dataStateCarrier = namespace() %}
        {%- do arguments['child']['operator'].getDataState(environment, arguments['child']['operator'], state, arguments['child'], dataStateCarrier) %}

        SELECT last_value(struct(*)) AS {{ environment.printSingleValueColumnName(environment) }} FROM ({{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }})
        {%- if state.aliasContext -%}
            {#- #} AS lastSource WHERE lastSource.{{ environment.printIDFromContext(environment, state.context) }} = {{ state.aliasContext }}.{{ environment.printIDFromContext(environment, state.context) }}
        {%- else -%}
            OVER {{ environment.printIDFromContext(environment, state.context) }}
        {%- endif -%}
    {%- endif -%}
{%- endmacro %}

{%- macro LastStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Last = namespace() %}
    {%- set environment.Last = Last %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Last) %}
    {%- set Last.defaultDataState = environment.DataStateEnum.SCALAR_STRUCT %}
    {%- set Last.defaultDataSQLFormat = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE %}
    {%- set Last.defaultAccessType = environment.AccessTypeEnum.SCALAR_STRUCT %}
    {%- set Last.print = LastPrint %}
{%- endmacro %}