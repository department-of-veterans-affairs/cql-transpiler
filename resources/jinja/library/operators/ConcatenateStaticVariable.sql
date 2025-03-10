{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ConcatenatePrint(environment, this, state, arguments) -%}
    {{ environment.logWarning(" concatenate is untested ") }}concat({{ environment.printOperatorsFromList(environment, state, arguments['children'], ", ") }})
{%- endmacro %}

{%- macro ConcatenateStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Concatenate = namespace() %}
    {%- set environment.Concatenate = Concatenate %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Concatenate) %}
    {%- set Concatenate.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW %}
    {%- set Concatenate.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Concatenate.print = ConcatenatePrint %}
{%- endmacro %}