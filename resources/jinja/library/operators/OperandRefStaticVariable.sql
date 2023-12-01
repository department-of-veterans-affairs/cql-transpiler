{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro OperandRefGetDataState(environment, this, state, arguments, carrier) %}
    {%- set referenceTo = state.functionArguments[arguments['referencedName']] %}
    {%- if referenceTo -%}
        {%- do referenceTo['operator'].getDataState(environment, referenceTo['operator'], state, referenceTo, carrier) %}
    {%- else -%}
        {%- set carrier.value = environment.DataStateEnum.SIMPLE %}
    {%- endif %}
{%- endmacro %}

{%- macro OperandRefGetDataSQLFormat(environment, this, state, arguments, carrier) %}
    {%- set referenceTo = state.functionArguments[arguments['referencedName']] %}
    {%- if referenceTo -%}
        {%- do referenceTo['operator'].getDataSQLFormat(environment, referenceTo['operator'], state, referenceTo, carrier) %}
    {%- else -%}
        {%- set carrier.value = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- endif %}
{%- endmacro %}

{%- macro OperandRefGetAccessType(environment, this, state, arguments, carrier) %}
    {%- set referenceTo = state.functionArguments[arguments['referencedName']] %}
    {%- if referenceTo -%}
        {%- do referenceTo['operator'].getAccessType(environment, referenceTo['operator'], state, referenceTo, carrier) %}
    {%- else -%}
        {#- Enable dot property access type for readability when printing function definitions -#}
        {%- set carrier.value = environment.AccessTypeEnum.DOT_PROPERTY %}
    {%- endif %}

    {%- if carrier.value in [none, Undefined, '', environment.AccessTypeEnum.UNDETERMINED, environment.AccessTypeEnum.INHERITED, environment.AccessTypeEnum.AUTO]  -%}
        {{ environment.logError(environment, "Unable to determine access type for operator "~this) }}
        {{0/0}}
    {%- endif %}
{%- endmacro %}

{%- macro OperandRefPrint(environment, this, state, arguments) -%}
    {%- set referenceTo = state.functionArguments[arguments['referencedName']] %}
    {%- if referenceTo -%}
            {{ referenceTo['operator'].print(environment, referenceTo['operator'], state, referenceTo) }}
    {%- else -%}
            {{ arguments['referencedName'] }}
    {%- endif %}
{%- endmacro %}

{%- macro OperandRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set OperandRef = namespace() %}
    {%- set environment.OperandRef = OperandRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.OperandRef) %}
    {%- set OperandRef.getDataSQLFormat = OperandRefGetDataSQLFormat %}
    {%- set OperandRef.getDataState = OperandRefGetDataState %}
    {%- set OperandRef.getAccessType = OperandRefGetAccessType %}
    {%- set OperandRef.print = OperandRefPrint %}
{%- endmacro %}