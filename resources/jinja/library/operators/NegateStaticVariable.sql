{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro NegatePrint(environment, this, state, arguments) -%}
    -{{ environment.coerceAndReformat(environment, state, arguments['child']['operator'], arguments['child'], none, none, true, namespace(), namespace()) }}
{%- endmacro %}

{%- macro NegateStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Negate = namespace() %}
    {%- set environment.Negate = Negate %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Negate) %}
    {%- set Negate.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set Negate.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set Negate.print = NegatePrint %}
{%- endmacro %}