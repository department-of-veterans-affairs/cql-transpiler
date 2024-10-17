{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro IfAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['then']['operator'].allowsSelectFromAccessType(environment, arguments['then']['operator'], carrier, state, arguments['then']) and arguments['else']['operator'].allowsSelectFromAccessType(environment, arguments['else']['operator'], carrier, state, arguments['else']) %}
{%- endmacro %}

{%- macro IfPrint(environment, this, state, arguments) -%}
    IF ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['condition'])}}) {# -#}
    THEN ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['then'])}}) {# -#}
    ELSE ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['else'])}})
{%- endmacro %}

{%- macro IfStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set If = namespace() %}
    {%- set environment.If = If %}
    {%- do environment.OperatorClass.construct(environment, none, environment.If) %}
    {%- set If.allowsSelectFromAccessType = IfAllowsSelectFromAccessType %}
    {#- TODO: it should be possible to determine the data type of an if statement by looking at its then/else statement #}
    {%- set If.defaultDataType = environment.DataTypeEnum.UNDETERMINED %}
    {%- set If.print = IfPrint %}
{%- endmacro %}