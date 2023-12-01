{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro FirstPrint(environment, this, state, arguments) -%}
    {%- if arguments['orderBy'] -%}
        {{ environment.logError(environment, "TODO: support First orderBy") }}
    {%- else -%}
        {#- find data state of child #}
        {%- set dataStateCarrier = namespace() %}
        {%- do arguments['child']['operator'].getDataState(environment, arguments['child']['operator'], state, arguments['child'], dataStateCarrier) %}

        SELECT first_value(struct(*)) AS {{ environment.printSingleValueColumnName(environment) }} FROM ({{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }})
        {%- if state.aliasContext -%}
            {#- #} AS firstSource WHERE firstSource.{{ environment.printIDFromContext(environment, state.context) }} = {{ state.aliasContext }}.{{ environment.printIDFromContext(environment, state.context) }}
        {%- else -%}
            OVER {{ environment.printIDFromContext(environment, state.context) }}
        {%- endif -%}
    {%- endif -%}
{%- endmacro %}

{%- macro FirstStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set First = namespace() %}
    {%- set environment.First = First %}
    {%- do environment.OperatorClass.construct(environment, none, environment.First) %}
    {%- set First.defaultDataState = environment.DataStateEnum.SCALAR_STRUCT %}
    {%- set First.defaultDataSQLFormat = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE %}
    {%- set First.defaultAccessType = environment.AccessTypeEnum.SCALAR_STRUCT %}
    {%- set First.print = FirstPrint %}
{%- endmacro %}