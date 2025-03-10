{%- macro DataSQLFormatEnumInit(environment) %}
    {%- set DataSQLFormatEnum = namespace() %}
    {%- set DataSQLFormatEnum.RAW_VALUE = 'sqlformat_rawValue' %}
    {%- set DataSQLFormatEnum.SINGLE_VALUE_TABLE = 'sqlformat_singleValueTable' %}
    {%- set DataSQLFormatEnum.SINGLE_VALUE_TABLE_REFERENCE = 'sqlformat_singleValueTableReference' %}
    {%- set DataSQLFormatEnum.QUERY = 'sqlformat_query' %}
    {%- set DataSQLFormatEnum.QUERY_REFERENCE = 'sqlformat_queryReference' %}
    {%- set DataSQLFormatEnum.INHERITED = 'sqlformat_inherited' %}
    {%- set DataSQLFormatEnum.UNDETERMINED = 'sqlformat_undetermined' %}
    {%- set environment.DataSQLFormatEnum = DataSQLFormatEnum %}
{%- endmacro %}
