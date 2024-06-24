{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro TupleElementPrint(environment, this, state, arguments) -%}
    {% set previousCoercionInstructions = state.coercionInstructions %}
    {% set state.coercionInstructions = { environment.DataTypeEnum.TABLE: environment.DataTypeEnum.ENCAPSULATED, environment.DataTypeEnum.SIMPLE: environment.DataTypeEnum.ENCAPSULATED } -%}
    {{ environment.OperatorHandler.print(environment, this, state, arguments['child']) }} AS {{ arguments['name'] }}
    {%- set state.coercionInstructions = previousCoercionInstructions %}
{%- endmacro %}

{%- macro TupleElementStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set TupleElement = namespace() %}
    {%- set environment.TupleElement = TupleElement %}
    {%- do environment.OperatorClass.construct(environment, none, environment.TupleElement) %}
    {%- set TupleElement.defaultDataType = environment.DataTypeEnum.ENCAPSULATED %}
    {%- set TupleElement.print = TupleElementPrint %}
{%- endmacro %}