{%- macro AccessTypeEnumInit(environment) %}
    {%- set AccessTypeEnum = namespace() %}
    {%- set AccessTypeEnum.INTERVAL = 'accesstype_interval'%}
    {%- set AccessTypeEnum.SCALAR_STRUCT = 'accesstype_scalarstruct'%}
    {%- set AccessTypeEnum.DOT_PROPERTY = 'accesstype_dotProperty' %}
    {%- set AccessTypeEnum.SELECT_FROM = 'accesstype_selectFrom' %}
    {%- set AccessTypeEnum.INHERITED = 'accesstype_inherited' %}
    {%- set AccessTypeEnum.AUTO = 'accesstype_auto' %}
    {%- set AccessTypeEnum.UNDETERMINED = 'accesstype_undetermined' %}
    {%- set environment.AccessTypeEnum = AccessTypeEnum %}
{%- endmacro %}
