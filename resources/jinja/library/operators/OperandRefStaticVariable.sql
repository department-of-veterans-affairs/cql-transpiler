{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro OperandRefAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- if state.functionArguments[arguments['referencedName']] -%}
        {%- do state.functionArguments[arguments['referencedName']]['operator'].allowsSelectFromAccessType(environment, state.functionArguments[arguments['referencedName']]['operator'], carrier, state, state.functionArguments[arguments['referencedName']]) %}
    {%- else -%}
        {#- Enable select from access type for readability when printing function definitions -#}
        {%- set carrier.value = true %}
    {%- endif %}
{%- endmacro %}

{%- macro OperandRefAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- if state.functionArguments[arguments['referencedName']] -%}
        {%- do state.functionArguments[arguments['referencedName']]['operator'].allowsDotPropertyAccessType(environment, state.functionArguments[arguments['referencedName']]['operator'], carrier, state, state.functionArguments[arguments['referencedName']]) %}
    {%- else -%}
        {#- Enable dot property access type for readability when printing function definitions -#}
        {%- set carrier.value = true %}
    {%- endif %}
{%- endmacro %}

{%- macro OperandRefPrint(environment, this, state, arguments) -%}
    {%- if state.functionArguments[arguments['referencedName']] -%}
            {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, state.functionArguments[arguments['referencedName']]) }}
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
    {%- set OperandRef.allowsSelectFromAccessType = OperandRefAllowsSelectFromAccessType %}
    {%- set OperandRef.allowsDotPropertyAccessType = OperandRefAllowsDotPropertyAccessType %}
    {%- set OperandRef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set OperandRef.print = OperandRefPrint %}
{%- endmacro %}