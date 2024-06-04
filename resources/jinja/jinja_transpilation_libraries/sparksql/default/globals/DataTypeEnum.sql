{% macro DataTypeEnumInit(environment) %}
{%-     set DataTypeEnum = namespace() %}
{%-     set DataTypeEnum.SIMPLE = 'simple' %}
{%-     set DataTypeEnum.ENCAPSULATED = 'encapsulated' %}
{%-     set DataTypeEnum.TABLE = 'table' %}
{%-     set DataTypeEnum.INHERITED = 'inherited' %}
{%-     set DataTypeEnum.STATEMENT = 'statement' %}
{%-     set DataTypeEnum.UNKNOWN = 'unknown' %}
{%-     set environment.DataTypeEnum = DataTypeEnum %}
{% endmacro %}