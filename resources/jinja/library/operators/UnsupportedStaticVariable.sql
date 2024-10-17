{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro UnsupportedPrint(environment, this, state, arguments) %}
    /* todo -- Unsupported Operator -- {{ arguments['unsupportedOperator'] }} with arguments <{{ arguments }}> */
{%- endmacro %}


{%- macro UnsupportedStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Unsupported = namespace() %}
    {%- set environment.Unsupported = Unsupported %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Unsupported) %}
    {%- set Unsupported.defaultDataType = environment.DataTypeEnum.UNDETERMINED %}
    {%- set Unsupported.print = UnsupportedPrint %}
{%- endmacro %}