{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro AliasedQuerySourcePrint(environment, this, state, arguments) -%}
    {#- QueryStaticVariable QueryPrint assumes responsiblity for handling AliasedQuerySource alias printing -#}
    {#- Parts of the source may previously have been encapsulated #}
    {%- set carrier = namespace() %}
    {%- do arguments['child']['operator'].getDataType(environment, arguments['child']['operator'], carrier, state, arguments['child']) -%}
    {{ environment.coerce(environment, carrier.value, environment.DataTypeEnum.TABLE, state, arguments['child']) }}
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