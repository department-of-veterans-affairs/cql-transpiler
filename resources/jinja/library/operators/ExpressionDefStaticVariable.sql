{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ExpressionDefGetDataSQLFormat(environment, this, state, arguments, carrier) %}
    {%- do arguments['child']['operator'].getDataSQLFormat(environment, arguments['child']['operator'], state, arguments['child'], carrier) %}

    {#- raw values must be rendered as tables #}
    {%- if carrier.value in [environment.DataSQLFormatEnum.RAW_VALUE]  -%}
        {%- set carrier.value = environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE%}
    {%- endif %}
{%- endmacro %}

{%- macro ExpressionDefPrint(environment, this, state, arguments) %}
    {%- set previousContext = state.context %}
    {%- if previousContext == none and arguments['context'] %}
        {%- set state.context = arguments['context'] %}
    {%- endif -%}
    {#- get child data format -- raw values are converted into single value tables #}
    {%- set carrier = namespace() %}
    {%- do arguments['child']['operator'].getDataSQLFormat(environment, arguments['child']['operator'], state, arguments['child'], carrier) %}
    {%- if carrier.value in [environment.DataSQLFormatEnum.RAW_VALUE]  -%}
        {{ environment.coerceAndReformat(environment, state, arguments['child']['operator'], arguments['child'], none, environment.DataSQLFormatEnum.SINGLE_VALUE_TABLE, false, namespace(), namespace()) }}
    {%- else -%}
        {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}
    {%- endif %}
{%- endmacro %}

{%- macro ExpressionDefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ExpressionDef = namespace() %}
    {%- set environment.ExpressionDef = ExpressionDef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ExpressionDef) %}
    {%- set ExpressionDef.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set ExpressionDef.defaultAccessType = environment.AccessTypeEnum.SELECT_FROM %}
    {%- set ExpressionDef.getDataSQLFormat = ExpressionDefGetDataSQLFormat %}
    {%- set ExpressionDef.print = ExpressionDefPrint %}
{%- endmacro %}