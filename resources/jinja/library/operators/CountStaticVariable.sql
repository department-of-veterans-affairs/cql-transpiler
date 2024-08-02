{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro CountPrint(environment, this, state, arguments) -%}
    {%- if state.context == 'Unfiltered' -%}
        SELECT count(*) FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
    {%- else -%}
        SELECT {{ environment.printIDFromContext(environment, state.context) }}, count(*) FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}) GROUP BY {{ environment.printIDFromContext(environment, state.context) }}
    {%- endif %}
{%- endmacro %}

{%- macro CountStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Count = namespace() %}
    {%- set environment.Count = Count %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Count) %}
    {%- set Count.print = CountPrint %}
    {%- set Count.defaultDataType = environment.DataTypeEnum.TABLE %}
{%- endmacro %}