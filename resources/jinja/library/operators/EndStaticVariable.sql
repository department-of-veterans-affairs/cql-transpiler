{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}
{%- from "library/globals/IntervalStaticVariables.sql" import IntervalStaticVariablesInit %}

{%- macro EndPrint(environment, this, state, arguments) -%}
    {%- set carrier = namespace() %}
    {%- do arguments['child']['operator'].getDataType(environment, arguments['child']['operator'], carrier, state, arguments['child']['arguments']) -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}.{{ environment.intervalEnd }}        
{%- endmacro %}

{%- macro EndStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {%- do IntervalStaticVariablesInit(environment) %}
    {#- initialize member variables #}
    {%- set End = namespace() %}
    {%- set environment.End = End %}
    {%- do environment.OperatorClass.construct(environment, none, environment.End) %}
    {%- set End.print = EndPrint %}
{%- endmacro %}