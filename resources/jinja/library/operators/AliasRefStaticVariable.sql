{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro AliasRefPrint(environment, this, state, arguments) -%}
    {{ arguments['referencedName'] }}
{%- endmacro %}

{%- macro AliasRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set AliasRef = namespace() %}
    {%- set environment.AliasRef = AliasRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.AliasRef) %}
    {%- set AliasRef.allowsSelectFromAccessTypeByDefault = true %}
    {%- set AliasRef.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set AliasRef.print = AliasRefPrint %}
{%- endmacro %}