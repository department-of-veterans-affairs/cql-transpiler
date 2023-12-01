{%- macro DataStateEnumInit(environment) %}
    {%- set DataStateEnum = namespace() %}
    {%- set DataStateEnum.SIMPLE = 'datastate_simple' %}
    {%- set DataStateEnum.SCALAR_STRUCT = 'datastate_scalarstruct' %}
    {%- set DataStateEnum.ENCAPSULATED = 'datastate_tableEncapsulatedAsListOfTuples' %}
    {%- set DataStateEnum.TABLE = 'datastate_table' %}
    {%- set DataStateEnum.NULL = 'datastate_null' %}
    {%- set DataStateEnum.INHERITED = 'datastate_inherited' %}
    {%- set DataStateEnum.UNDETERMINED = 'datastate_undetermined' %}
    {%- set environment.DataStateEnum = DataStateEnum %}
{%- endmacro %}
