{%- from "library/globals/ContextHandlingFunctions.sql" import ContextHandlingFunctionsInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro SingletonFromPrint(environment, this, state, arguments) -%}
    {%- if state.aliasContext -%}
        SELECT * FROM ({{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}) AS SingletonFrom WHERE SingletonFrom.{{ environment.printIDFromContext(environment, state.context) }} = {{ state.aliasContext }}.{{ environment.printIDFromContext(environment, state.context) }} LIMIT 1
    {%- else -%}
        SELECT * FROM ({{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}) AS SingletonFrom{{ environment.logError(environment, "No alias context defined; unable to narrow down SingletonFrom") }} LIMIT 1
    {%- endif -%}
{%- endmacro %}

{%- macro SingletonFromStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do ContextHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set SingletonFrom = namespace() %}
    {%- set environment.SingletonFrom = SingletonFrom %}
    {%- do environment.OperatorClass.construct(environment, none, SingletonFrom) %}
    {%- set SingletonFrom.defaultDataState = environment.DataStateEnum.TABLE %}
    {%- set SingletonFrom.defaultDataSQLFormat = environment.DataSQLFormatEnum.QUERY %}
    {%- set SingletonFrom.defaultAccessType = environment.AccessTypeEnum.SELECT_FROM %}
    {%- set SingletonFrom.defaultRequiresContext = environment.RequiresContextEnum.TRUE %}
    {%- set SingletonFrom.print = SingletonFromPrint %}
{%- endmacro %}