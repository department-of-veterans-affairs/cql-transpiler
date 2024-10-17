{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}


{%- macro PropertyClassGetDataType(environment, this, carrier, state, arguments) %}
    {%- set carrier.value = this.defaultDataType %}
    {%- do environment.OperatorClass.getDataType(environment, this, carrier, state, arguments) %}
    {%- if carrier.value == environment.DataTypeEnum.UNDETERMINED -%}
        {%- set carrier.value = environment.DataTypeEnum.UNDETERMINED %}
    {%- else -%}
        {%- set carrier.value = environment.DataTypeEnum.ENCAPSULATED %}
    {%- endif %}
{%- endmacro %}

{%- macro TupleElementPrint(environment, this, state, arguments) -%}
    {%- set operatorDataTypeEnumCarrier = namespace() %}
    {%- do arguments['child']['operator'].getDataType(environment, arguments['child']['operator'], operatorDataTypeEnumCarrier, state, arguments['child']) -%}
    {%- if carrier.value == SIMPLE -%}
        (SELECT {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }} {{ environment.printSingleValueColumnName() }}) {# -#}
    {%- else -%}
        {{ environment.coerce(environment, operatorDataTypeEnumCarrier.value, environment.DataTypeEnum.ENCAPSULATED, state, arguments['child']) }} {# -#}
    {%- endif %}
    AS {{ arguments['name'] }}
{%- endmacro %}

{%- macro TupleElementStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set TupleElement = namespace() %}
    {%- set environment.TupleElement = TupleElement %}
    {%- do environment.OperatorClass.construct(environment, none, environment.TupleElement) %}
    {%- set TupleElement.allowsSelectFromAccessTypeByDefault = true %}
    {%- set TupleElement.print = TupleElementPrint %}
{%- endmacro %}