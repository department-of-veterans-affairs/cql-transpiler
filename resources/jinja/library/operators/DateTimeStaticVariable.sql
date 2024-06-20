{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro DateTimePrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, this, state, arguments['year']) }}-{{ environment.OperatorHandler.print(environment, this, state, arguments['month']) }}-{{ environment.OperatorHandler.print(environment, this, state, arguments['day']) }}T{{ environment.OperatorHandler.print(environment, this, state, arguments['hour']) }}:{{ environment.OperatorHandler.print(environment, this, state, arguments['minute']) }}:{{ environment.OperatorHandler.print(environment, this, state, arguments['second']) }}.{{ environment.OperatorHandler.print(environment, this, state, arguments['millisecond']) }}Z
{%- endmacro %}

{%- macro DateTimeStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set DateTime = namespace() %}
    {%- set environment.DateTime = DateTime %}
    {%- do environment.OperatorClass.construct(environment, none, environment.DateTime) %}
    {%- set DateTime.print = DateTimePrint %}
{%- endmacro %}