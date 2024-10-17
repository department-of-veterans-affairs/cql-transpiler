{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro TuplePrint(environment, this, state, arguments) -%}
    SELECT struct(*) FROM ({{ environment.printOperatorsFromList(environment, state, arguments['children'], ', ') }})
{%- endmacro %}

{%- macro TupleStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Tuple = namespace() %}
    {%- set environment.Tuple = Tuple %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Tuple) %}
    {%- set Tuple.print = TuplePrint %}
    {%- set Tuple.allowsSelectFromAccessTypeByDefault = true %}
    {%- set Tuple.defaultDataType = environment.DataTypeEnum.TABLE %}
{%- endmacro %}