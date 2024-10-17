{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro FunctionRefAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['referenceTo']['operator'].allowsSelectFromAccessType(environment, arguments['referenceTo']['operator'], carrier, state, arguments['referenceTo']) %}
{%- endmacro %}

{%- macro FunctionRefAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['referenceTo']['operator'].allowsDotPropertyAccessType(environment, arguments['referenceTo']['operator'], carrier, state, arguments['referenceTo']) %}
{%- endmacro %}

{%- macro FunctionRefPrint(environment, this, state, arguments) -%}
    {%- set previousFunctionArguments = state.functionArguments %}
    {%- set functionArguments = previousFunctionArguments.copy() %}
    {%- for value in arguments['children'] %}
        {#- When a function argument is a reference, inline it #}
        {%- if value.operator == environment.OperandRef %}
            {#- OperandRefs are always references to OperandDefs #}
            {%- do functionArguments.update({arguments['referenceTo']['arguments'][loop.index - 1]['referenceName'] : functionArguments[value.referencedName]}) %}
        {%- else %}
            {%- do functionArguments.update({arguments['referenceTo']['arguments'][loop.index - 1]['referenceName'] : value}) %}
        {%- endif %}
    {%- endfor %}
    {%- set state.functionArguments = functionArguments -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['referenceTo']) }}
    {%- set state.functionArguments = previousFunctionArguments %}
{%- endmacro %}

{%- macro FunctionRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set FunctionRef = namespace() %}
    {%- set environment.FunctionRef = FunctionRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.FunctionRef) %}
    {%- set FunctionRef.allowsSelectFromAccessType = FunctionRefAllowsSelectFromAccessType %}
    {%- set FunctionRef.allowsDotPropertyAccessType = FunctionRefAllowsDotPropertyAccessType %}
    {%- set FunctionRef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set FunctionRef.print = FunctionRefPrint %}
{%- endmacro %}