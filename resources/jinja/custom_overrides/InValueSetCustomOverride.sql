{%- from "custom_overrides/ValueSetUtilityMacros.sql" import valuesetCodes %}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/operators/InValueSetStaticVariable.sql" import InValueSetStaticVariableInit %}

{%- macro InValueSetPrintCustomOverride(environment, this, state, arguments) -%}
    {%- if arguments['valueSetExpression'] -%}
        {%- set valueSet = arguments['valueSetExpression'] %}
    {%- else -%}
        {%- set valueSet = arguments['valueSetReference'] %}
    {%- endif -%}
    EXISTS (SELECT codes.code AS code FROM ({{ valuesetCodes(environment, state, valueSet, asOfDate) }}) WHERE code = {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }})
{%- endmacro %}

{%- macro InValueSetCustomOverrideInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do InValueSetStaticVariableInit(environment) %}
    {# initialize member variables #}
    {%- set InValueSet = environment.InValueSet %}
    {%- set InValueSet.print = InValueSetPrintCustomOverride %}
{%- endmacro %}