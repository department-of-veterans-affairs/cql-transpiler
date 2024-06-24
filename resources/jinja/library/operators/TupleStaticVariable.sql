{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro TuplePrint(environment, this, state, arguments) -%}
    SELECT struct(*) FROM ({{ environment.printOperatorsFromList(environment, state, arguments['children'], ', ') }})
{%- endmacro %}

{%- macro TupleStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Tuple = namespace() %}
    {%- set environment.Tuple = Tuple %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Tuple) %}
    {%- set Tuple.defaultDataType = environment.DataTypeEnum.ENCAPSULATED %}
    {%- set Tuple.print = TuplePrint %}
{%- endmacro %}