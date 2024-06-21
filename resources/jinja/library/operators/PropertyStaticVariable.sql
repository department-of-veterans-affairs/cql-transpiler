{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro PropertyPrint(environment, this, state, arguments) -%}
    {%- if arguments['child'] != none -%}
        {{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }}.{{ arguments['path'] }}
    {%- elif arguments['path'] != none -%}
        {{ arguments['scope'] }}.{{ arguments['path'] }}
    {%- else -%}
        {{ arguments['scope'] }}
    {%- endif %}
{%- endmacro %}

{%- macro PropertyStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {# initialize member variables #}
    {%- set Property = namespace() %}
    {%- set environment.Property = Property %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Property) %}
    {%- set Property.defaultDataType = environment.DataTypeEnum.ENCAPSULATED %}
    {%- set Property.print = PropertyPrint %}
{%- endmacro %}