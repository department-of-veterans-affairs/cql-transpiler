{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro AliasRefPrint(environment, this, state, arguments) -%}
    SELECT _aliasRef_{{ arguments['referencedName'] }}.* FROM (SELECT _aliasRef_{{ arguments['referencedName'] }})
{%- endmacro %}

{%- macro AliasRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set AliasRef = namespace() %}
    {%- set environment.AliasRef = AliasRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.AliasRef) %}
    {%- set AliasRef.defaultDataSQLFormat = environment.DataSQLFormatEnum.QUERY %}
    {%- set AliasRef.defaultDataState = environment.DataStateEnum.TABLE %}
    {%- set AliasRef.defaultAccessType = environment.AccessTypeEnum.DOT_PROPERTY %}
    {%- set AliasRef.print = AliasRefPrint %}
{%- endmacro %}