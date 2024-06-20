{#    
    Environment prerequisites:
        * UnsupportedOperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/UnsupportedOperatorClass.sql" import UnsupportedOperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro UnsupportedOperatorStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do UnsupportedOperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {# initialize member variables #}
    {%- set Unsupported = namespace() %}
    {%- set Unsupported.defaultDataType = environment.DataTypeEnum.UNKNOWN %}
    {%- do environment.UnsupportedOperatorClass.construct(environment, Unsupported) %}
    {%- set environment.Unsupported = Unsupported %}
{%- endmacro %}