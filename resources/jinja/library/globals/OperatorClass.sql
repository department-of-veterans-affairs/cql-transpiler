
{%- macro OperatorClassGetDataType(environment, this, carrier, state, arguments) %}
{%-     set carrier.value = this.defaultDataType %}
{%- endmacro %}

{%- macro OperatorClassPrint(environment, this, state, arguments) %}
{%-     set previousInsideSqlComment = state.insideSqlComment %}
{%-     set state.insideSqlComment = true %}
{%-     if not previousInsideSqlComment %}
/* 
{%-     endif %}
Operator with state: {{ state }} and arguments: {{ arguments }} 
{%-     if not previousInsideSqlComment %}
 */ 
{%-     endif %}
{%-     set state.insideSqlComment = previousInsideSqlComment %}
{%- endmacro %}

{%- macro OperatorClassConstruct(environment, state, operatorNamespace) %}
{%-     set operatorNamespace.defaultDataType = environment.DataTypeEnum.SIMPLE %}
{%-     set operatorNamespace.getDataType = OperatorClassGetDataType %}
{%-     set operatorNamespace.print = OperatorClassPrint %}
{%- endmacro %}

{% macro OperatorClassInit(environment) -%}
{%-     set OperatorClass = namespace() %}
{%-     set OperatorClass.construct = OperatorClassConstruct %}
{%-     set environment.OperatorClass = OperatorClass %}
{% endmacro %}