{#
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{% from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro InValueSetPrint(environment, this, state, arguments) -%}
/* Unsupported InValueSet
{%-     if arguments['child'] != none %}
 with child: <{{ environment.OperatorHandler.print(environment, this, state, arguments['child'])}}>
{%-     endif %}
{%-     if arguments['valueSetReference'] == none %}
 valueSetExpression: <{{ environment.OperatorHandler.print(environment, this, state, arguments['valueSetExpression'])}}>
{%-     else %}
 valueSetReference: <{{ environment.OperatorHandler.print(environment, this, state, arguments['valueSetReference'])}}>
{%-     endif %}
*/
{%- endmacro %}

{% macro InValueSetStaticVariableInit(environment) %}
{# initialize prerequisites #}
{%-   do OperatorHandlerStaticVariableInit(environment) %}
{%-   do OperatorClassInit(environment) %}
{%-   do DataTypeEnumInit(environment) %}
{# initialize member variables #}
{%-     set InValueSet = namespace() %}
{%-     set environment.InValueSet = InValueSet %}
{%-     do environment.OperatorClass.construct(environment, none, environment.InValueSet) %}
{%-     set InValueSet.print = InValueSetPrint %}
{%- endmacro %}