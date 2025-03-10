{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro IsNullPrint(environment, this, state, arguments) -%}
    {{ environment.coerceAndReformat(environment, state, arguments['child']['operator'], arguments['child'], none, none, true, namespace(), namespace()) }} IS NULL
{%- endmacro %}

{%- macro IsNullStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set IsNull = namespace() %}
    {%- set environment.IsNull = IsNull %}
    {%- do environment.OperatorClass.construct(environment, none, environment.IsNull) %}
    {%- set IsNull.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW %}
    {%- set IsNull.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set IsNull.print = IsNullPrint %}
{%- endmacro %}