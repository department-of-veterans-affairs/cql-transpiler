{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro resultTypeToDataState(environment, resultTypeAsString, carrier) -%}
    {% set firstEntry = resultTypeAsString.split('<')[0] %}
    {%- if firstEntry == 'interval' -%}
        {%- set carrier.value = environment.DataStateEnum.SIMPLE %}
    {%- else %}
        {{ 0/0 }}
        {%- set carrier.value = environment.DataStateEnum.UNDETERMINED %}
    {%- endif %}
{%- endmacro %}

{%- macro resultTypeToSQLFormat(environment, resultTypeAsString, carrier) -%}
    {% set firstEntry = resultTypeAsString.split('<')[0] %}
    {%- if firstEntry == 'interval' -%}
        {%- set carrier.value = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- else %}
        {{ 0/0 }}
        {%- set carrier.value = environment.DataSQLFormatEnum.UNDETERMINED %}
    {%- endif %}
{%- endmacro %}

{%- macro resultTypeToAccessType(environment, resultTypeAsString, carrier) -%}
    {% set firstEntry = resultTypeAsString.split('<')[0] %}
    {%- if firstEntry == 'interval' -%}
        {%- set carrier.value = environment.AccessTypeEnum.DOT_PROPERTY %}
    {%- else %}
        {{ 0/0 }}
        {%- set carrier.value = environment.AccessTypeEnum.UNDETERMINED %}
    {%- endif %}
{%- endmacro %}

{%- macro IdentifierRefGetDataState(environment, this, state, arguments, carrier) -%}
    {%- do resultTypeToDataState(environment, arguments['resultType'], carrier) %}
{%- endmacro %}

{%- macro IdentifierRefGetDataSQLFormat(environment, this, state, arguments, carrier) -%}
    {%- do resultTypeToSQLFormat(environment, arguments['resultType'], carrier) %}
{%- endmacro %}

{%- macro IdentifierRefGetAccessType(environment, this, state, arguments, carrier) -%}
    {%- do resultTypeToAccessType(environment, arguments['resultType'], carrier) %}

    {%- if carrier.value in [none, Undefined, '', environment.AccessTypeEnum.UNDETERMINED, environment.AccessTypeEnum.INHERITED, environment.AccessTypeEnum.AUTO]  -%}
        {{ environment.logError(environment, "Unable to determine access type for operator "~this) }}
        {{0/0}}
    {%- endif %}
{%- endmacro %}

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
    {%- set IdentifierRef.getDataState= IdentifierRefGetDataState %}
    {%- set IdentifierRef.getDataSQLFormat= IdentifierRefGetDataSQLFormat %}
    {%- set IdentifierRef.getAccessType= IdentifierRefGetAccessType %}
    {%- set IdentifierRef.print = IdentifierRefPrint %}
{%- endmacro %}