{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro IsNullPrint(environment, this, state, arguments) -%}
{{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }} AS {{ arguments['alias'] }} SUCH THAT {{ arguments['suchThat'] }}
{%- endmacro %}

{% macro IsNullStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set IsNull = namespace() %}
{%-     set environment.IsNull = IsNull %}
{%-     do environment.OperatorClass.construct(environment, none, environment.IsNull) %}
{%-     set IsNull.defaultDataType = environment.DataTypeEnum.INHERITED %}
{%-     set IsNull.print = IsNullPrint %}
{%- endmacro %}