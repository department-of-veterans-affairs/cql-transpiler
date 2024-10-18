{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro PropertyAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- if arguments['child'] -%}
            {%- do arguments['child']['operator'].allowsSelectFromAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
    {%- else %}
            {%- set carrier.value = false %}
    {%- endif %}
{%- endmacro %}

{%- macro PropertyAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- if arguments['child'] -%}
        {%- do arguments['child']['operator'].allowsDotPropertyAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
    {%- else -%}
        {%- set carrier.value = true %}
    {%- endif %}
{%- endmacro %}

{%- macro PropertyGetDataType(environment, this, carrier, state, arguments) %}
    {%- set carrier.value = this.defaultDataType %}
    {%- if child -%}
        {%- do environment.OperatorClass.getDataType(environment, this, carrier, state, arguments) %}
    {%- else -%}
        {%- set carrier.value = environment.DataTypeEnum.UNDETERMINED %}
    {%- endif %}
{%- endmacro %}

{%- macro PropertyPrint(environment, this, state, arguments) -%}
    {%- if arguments['child'] != none -%}
        {%- set dotPropertyCarrier = namespace() %}
        {%- do arguments['child']['operator'].allowsDotPropertyAccessType(environment, arguments['child']['operator'], dotPropertyCarrier, state, arguments['child']) %}
        {%- if dotPropertyCarrier.value -%}
            {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}.{{ arguments['path'] }}
        {%- else -%}
            {%- set selectFromCarrier = namespace() %}
            {%- do arguments['child']['operator'].allowsSelectFromAccessType(environment, arguments['child']['operator'], selectFromCarrier, state, arguments['child']) %}
            {%- if selectFromCarrier.value -%}
                (SELECT {{ arguments['path'] }} FROM ({{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}))
            {%- else -%}
                /* unable to determine access type for child of Property with path {{ arguments['path'] }} */ -> {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }}
            {%- endif %}
        {%- endif %}
    {%- elif arguments['path'] != none -%}
        {{ arguments['scope'] }}.{{ arguments['path'] }}
    {%- else -%}
        {{ arguments['scope'] }}
    {%- endif %}
{%- endmacro %}

{%- macro PropertyStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {# initialize member variables #}
    {%- set Property = namespace() %}
    {%- set environment.Property = Property %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Property) %}
    {%- set Property.allowsSelectFromAccessType = PropertyAllowsSelectFromAccessType %}
    {%- set Property.allowsDotPropertyAccessType = PropertyAllowsDotPropertyAccessType %}
    {%- set Property.getDataType = PropertyGetDataType %}
    {%- set Property.print = PropertyPrint %}
{%- endmacro %}