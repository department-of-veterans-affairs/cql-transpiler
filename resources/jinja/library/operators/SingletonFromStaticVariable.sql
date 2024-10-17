{%- from "library/globals/ContextHandlingFunctions.sql" import ContextHandlingFunctionsInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro SingletonFromPrint(environment, this, state, arguments) -%}
    {%- if state.aliasContext -%}
        SELECT * FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}) AS SingletonFrom WHERE SingletonFrom.{{ environment.printIDFromContext(environment, state.context) }} = {{ state.aliasContext }}.{{ environment.printIDFromContext(environment, state.context) }} LIMIT 1
    {%- else -%}
        SELECT * FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}) AS SingletonFrom /* no alias context defined; unable to narrow down SingletonFrom */ LIMIT 1
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
    {%- set SingletonFrom.allowsSelectFromAccessTypeByDefault = true %}
    {%- set SingletonFrom.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set SingletonFrom.print = SingletonFromPrint %}
{%- endmacro %}