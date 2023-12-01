{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro CalculateAgeAtPrint(environment, this, state, arguments) -%}
    DATEDIFF(year, {{ environment.printOperatorsFromListCoercing(environment, state, [arguments['left'], arguments['right']], ', ', none, true) }})
{%- endmacro %}

{%- macro CalculateAgeAtStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set CalculateAgeAt = namespace() %}
    {%- set environment.CalculateAgeAt = CalculateAgeAt %}
    {%- do environment.OperatorClass.construct(environment, none, environment.CalculateAgeAt) %}
    {%- set CalculateAgeAt.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set CalculateAgeAt.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set CalculateAgeAt.print = CalculateAgeAtPrint %}
{%- endmacro %}