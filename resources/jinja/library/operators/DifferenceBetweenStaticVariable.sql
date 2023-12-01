{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro DifferenceBetweenPrint(environment, this, state, arguments) -%}
    DATEDIFF({{ environment.printOperatorsFromListCoercing(environment, state, [arguments['right'], arguments['left']], ', ', none, true) }})
{%- endmacro %}

{%- macro DifferenceBetweenStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set DifferenceBetween = namespace() %}
    {%- set environment.DifferenceBetween = DifferenceBetween %}
    {%- do environment.OperatorClass.construct(environment, none, environment.DifferenceBetween) %}
    {%- set DifferenceBetween.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW %}
    {%- set DifferenceBetween.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set DifferenceBetween.print = DifferenceBetweenPrint %}
{%- endmacro %}