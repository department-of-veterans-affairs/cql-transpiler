{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{% macro initFunctionRef(environment, this, state, arguments) %}
    {%- set functionArguments = state.functionArguments %}
    {%- for value in arguments['children'] %}
        {#- When a function argument is a reference, inline it #}
        {%- if value.operator == environment.OperandRef %}
            {#- OperandRefs are always references to OperandDefs #}
            {%- do functionArguments.update({arguments['referenceTo']['arguments'][loop.index - 1]['referenceName'] : functionArguments[value.referencedName]}) %}
        {%- else %}
            {%- do functionArguments.update({arguments['referenceTo']['arguments'][loop.index - 1]['referenceName'] : value}) %}
        {%- endif %}
    {%- endfor %}
{% endmacro %}

{%- macro FunctionRefGetDataState(environment, this, state, arguments, carrier) %}
    {%- set previousFunctionArguments = state.functionArguments.copy() %}
    {%- do initFunctionRef(environment, this, state, arguments) %}
    {%- do arguments['referenceTo']['operator'].getDataState(environment, arguments['referenceTo']['operator'], state, arguments['referenceTo'], carrier) %}
    {%- set state.functionArguments = previousFunctionArguments %}
    {%- if carrier.value in [none, Undefined, '', environment.DataStateEnum.UNDETERMINED, environment.DataStateEnum.INHERITED]  -%}
        {{ environment.logError(environment, "Unable to determine data state for operator "~this) }}
    {%- endif %}
{%- endmacro %}

{%- macro FunctionRefGetDataSQLFormat(environment, this, state, arguments, carrier) %}
    {%- set previousFunctionArguments = state.functionArguments.copy() %}
    {%- do initFunctionRef(environment, this, state, arguments) %}
    {%- do arguments['referenceTo']['operator'].getDataSQLFormat(environment, arguments['referenceTo']['operator'], state, arguments['referenceTo'], carrier) %}
    {%- set state.functionArguments = previousFunctionArguments %}
    {%- if carrier.value in [none, Undefined, '', environment.DataSQLFormatEnum.UNDETERMINED, environment.DataSQLFormatEnum.INHERITED]  -%}
        {{ environment.logError(environment, "Unable to determine data format for operator "~this) }}
    {%- endif %}
{%- endmacro %}

{%- macro FunctionGetAccessType(environment, this, state, arguments, carrier) %}
    {%- set previousFunctionArguments = state.functionArguments.copy() %}
    {%- do initFunctionRef(environment, this, state, arguments) %}
    {%- do environment.OperatorClass.getAccessType(environment, this, state, arguments, carrier) %}
    {%- set state.functionArguments = previousFunctionArguments %}

    {%- if carrier.value in [none, Undefined, '', environment.AccessTypeEnum.UNDETERMINED, environment.AccessTypeEnum.INHERITED, environment.AccessTypeEnum.AUTO]  -%}
        {{ environment.logError(environment, "Unable to determine access type for operator "~this) }}
        {{0/0}}
    {%- endif %}
{%- endmacro %}

{%- macro FunctionRefPrint(environment, this, state, arguments) -%}
    {%- set previousFunctionArguments = state.functionArguments.copy() %}
    {%- do initFunctionRef(environment, this, state, arguments) -%}
    {{ arguments['referenceTo']['operator'].print(environment, arguments['referenceTo']['operator'], state, arguments['referenceTo']) }}
    {%- set state.functionArguments = previousFunctionArguments %}
{%- endmacro %}

{%- macro FunctionRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set FunctionRef = namespace() %}
    {%- set environment.FunctionRef = FunctionRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.FunctionRef) %}
    {%- set FunctionRef.defaultAccessType = environment.AccessTypeEnum.INHERITED %}
    {%- set FunctionRef.getDataState = FunctionRefGetDataState %}
    {%- set FunctionRef.getDataSQLFormat = FunctionRefGetDataSQLFormat %}
    {%- set FunctionRef.getAccessType = FunctionGetAccessType %}
    {%- set FunctionRef.print = FunctionRefPrint %}
{%- endmacro %}