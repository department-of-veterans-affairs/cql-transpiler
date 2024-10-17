{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro AliasedQuerySourcePrint(environment, this, state, arguments) -%}
    {#- QueryStaticVariable QueryPrint assumes responsiblity for handling AliasedQuerySource alias printing -#}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}
{%- endmacro %}

{%- macro AliasedQuerySourceStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set AliasedQuerySource = namespace() %}
    {%- set environment.AliasedQuerySource = AliasedQuerySource %}
    {%- do environment.OperatorClass.construct(environment, none, environment.AliasedQuerySource) %}
    {%- set AliasedQuerySource.print = AliasedQuerySourcePrint %}
    {%- set AliasedQuerySource.allowsSelectFromAccessTypeByDefault = true %}
    {%- set AliasedQuerySource.defaultDataType = environment.DataTypeEnum.TABLE %}
{%- endmacro %}