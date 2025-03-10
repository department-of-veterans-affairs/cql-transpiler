{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro ExpressionRefPrint(environment, this, state, arguments) -%}
    {%- set requiresContextCarrier = namespace() %}
    {%- do arguments['referenceTo']['operator'].getRequiresContext(environment, arguments['referenceTo']['operator'], state, arguments['referenceTo'], requiresContextCarrier) %}
    {%- if requiresContextCarrier.value == environment.RequiresContextEnum.TRUE -%}
        {{ arguments['referenceTo']['operator'].print(environment, arguments['referenceTo']['operator'], state, arguments['referenceTo']) }}
    {%- else -%}
        SELECT * FROM {{ '{{ ref(\'' }}{{ arguments['referencedName'] }}{{ '\') }}' }}
    {%- endif %}
{%- endmacro %}

{%- macro ExpressionRefStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set ExpressionRef = namespace() %}
    {%- set environment.ExpressionRef = ExpressionRef %}
    {%- do environment.OperatorClass.construct(environment, none, environment.ExpressionRef) %}
    {%- set ExpressionRef.defaultDataSQLFormat = environment.DataSQLFormatEnum.INHERITED %}
    {%- set ExpressionRef.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set ExpressionRef.defaultAccessType = environment.AccessTypeEnum.INHERITED %}
    {%- set ExpressionRef.defaultRequiresContext = environment.RequiresContextEnum.FALSE %}
    {%- set ExpressionRef.print = ExpressionRefPrint %}
{%- endmacro %}