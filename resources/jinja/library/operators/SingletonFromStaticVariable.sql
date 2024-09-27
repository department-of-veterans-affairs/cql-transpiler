{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
#}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/ContextHandlingFunctions.sql" import ContextHandlingFunctionsInit %}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro SingletonFromPrint(environment, this, state, arguments) -%}
    SELECT * FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}) AS SingletonFrom WHERE SingletonFrom.{{ environment.printIDFromContext(environment, state.context) }} = {% if state.aliasContext %}{{ state.aliasContext}}.{{ environment.printIDFromContext(environment, state.context) }}{% else %}/* no alias context defined */{% endif %} LIMIT 1
{%- endmacro %}

{%- macro SingletonFromStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {%- do ContextHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set SingletonFrom = namespace() %}
    {%- do environment.OperatorClass.construct(environment, none, SingletonFrom) %}
    {%- set SingletonFrom.print = SingletonFromPrint %}
    {%- set environment.SingletonFrom = SingletonFrom %}
{%- endmacro %}