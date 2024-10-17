{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro IdentifierRefPrint(environment, this, state, arguments) -%}
    {{ arguments['referencedName'] }}
{%- endmacro %}

{%- macro IdentifierRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set IdentifierRef = namespace() %}
    {%- set environment.IdentifierRef = IdentifierRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.IdentifierRef) %}
    {%- set IdentifierRef.allowsDotPropertyAccessTypeByDefault = true %}
    {%- set IdentifierRef.allowsSelectFromAccessTypeByDefault = true %}
    {#- TODO: is it possible to determine the data type of an IdentifierRef? #}
    {%- set IdentifierRef.defaultDataType = environment.DataTypeEnum.UNDETERMINED %}
    {%- set IdentifierRef.print = IdentifierRefPrint %}
{%- endmacro %}