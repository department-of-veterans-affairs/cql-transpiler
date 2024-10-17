{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro QueryLetRefAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['referenceTo']['operator'].allowsSelectFromAccessType(environment, arguments['referenceTo']['operator'], carrier, state, arguments['referenceTo']) %}
{%- endmacro %}

{%- macro QueryLetRefAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['referenceTo']['operator'].allowsDotPropertyAccessType(environment, arguments['referenceTo']['operator'], carrier, state, arguments['referenceTo']) %}
{%- endmacro %}

{%- macro QueryLetRefPrint(environment, this, state, arguments) -%}
    {{ arguments['referencedName'] }}
{%- endmacro %}

{%- macro QueryLetRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set QueryLetRef = namespace() %}
    {%- set environment.QueryLetRef = QueryLetRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.QueryLetRef) %}
    {%- set QueryLetRef.allowsSelectFromAccessType = QueryLetRefAllowsSelectFromAccessType %}
    {%- set QueryLetRef.allowsDotPropertyAccessType = QueryLetRefAllowsDotPropertyAccessType %}
    {%- set QueryLetRef.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set QueryLetRef.print = QueryLetRefPrint %}
{%- endmacro %}