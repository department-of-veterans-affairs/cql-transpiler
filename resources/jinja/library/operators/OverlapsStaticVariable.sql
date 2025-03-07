{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro OverlapsPrint(environment, this, state, arguments) -%}
    {#- #}(
        {#- #}{{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }}.start 
        {#- #}<= 
        {#- #}{{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }}.end 
        {#- #}AND 
        {#- #}{{ environment.coerceAndReformat(environment, state, arguments['left']['operator'], arguments['left'], none, none, true, namespace(), namespace()) }}.end 
        {#- #}>= 
        {#- #}{{ environment.coerceAndReformat(environment, state, arguments['right']['operator'], arguments['right'], none, none, true, namespace(), namespace()) }}.start
    {#- #})
{%- endmacro %}

{%- macro OverlapsStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Overlaps = namespace() %} 
    {%- set environment.Overlaps = Overlaps %} 
    {%- do environment.OperatorClass.construct(environment, none, environment.Overlaps) %} 
    {%- set Overlaps.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %} 
    {%- set Overlaps.defaultDataState = environment.DataStateEnum.SIMPLE %} 
    {%- set Overlaps.print = OverlapsPrint %} 
{%- endmacro %}
