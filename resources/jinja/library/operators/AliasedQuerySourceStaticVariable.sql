{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ConversionHandlingFunctions.sql" import ConversionHandlingFunctionsInit %}

{%- macro AliasedQuerySourcePrint(environment, this, state, arguments) -%}
    {{ environment.coerceAndReformat(environment, state, arguments['child']['operator'], arguments['child'], environment.DataStateEnum.TABLE, environment.DataSQLFormatEnum.QUERY, false, namespace(), namespace()) }}
{%- endmacro %}

{%- macro AliasedQuerySourceStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do ConversionHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set AliasedQuerySource = namespace() %}
    {%- set environment.AliasedQuerySource = AliasedQuerySource %}
    {%- do environment.OperatorClass.construct(environment, none, environment.AliasedQuerySource) %}
    {%- set AliasedQuerySource.defaultDataState = environment.DataStateEnum.TABLE %}
    {%- set AliasedQuerySource.defaultDataSQLFormat = environment.DataSQLFormatEnum.QUERY %}
    {%- set AliasedQuerySource.defaultAccessType = environment.AccessTypeEnum.SELECT_FROM %}
    {%- set AliasedQuerySource.print = AliasedQuerySourcePrint %}
{%- endmacro %}