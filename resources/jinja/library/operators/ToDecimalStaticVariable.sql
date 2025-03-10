{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ToDecimalPrint(environment, this, state, arguments) -%}
    (0.0 + {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }})
{%- endmacro %}

{%- macro ToDecimalStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ToDecimal = namespace() %}
    {%- set environment.ToDecimal = ToDecimal %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ToDecimal) %}
    {%- set ToDecimal.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set ToDecimal.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set ToDecimal.print = ToDecimalPrint %}
{%- endmacro %}