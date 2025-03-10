{%- from "library/operators/InValueSetStaticVariable.sql" import InValueSetStaticVariableInit %}

{%- macro InValueSetPrintCustomOverride(environment, this, state, arguments) -%}
    {%- if arguments['valueSetExpression'] -%}
        {%- set valueSet = arguments['valueSetExpression'] %}
    {%- else -%}
        {%- set valueSet = arguments['valueSetReference'] %}
    {%- endif -%}
    EXISTS (SELECT codes.code AS code FROM ({{ "{{ " }}transpilerValuesetCodes({{ "'" }}{{ valueSet['operator'].print(environment, valueSet['operator'], state, valueSet) }}{{ "'" }}, none){{ " }}" }}) WHERE code = {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }})
{%- endmacro %}

{%- macro InValueSetCustomOverrideInit(environment) %}
    {#- initialize prerequisites #}
    {%- do InValueSetStaticVariableInit(environment) %}
    {# initialize member variables #}
    {%- set InValueSet = environment.InValueSet %}
    {%- set InValueSet.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set InValueSet.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set InValueSet.print = InValueSetPrintCustomOverride %}
{%- endmacro %}