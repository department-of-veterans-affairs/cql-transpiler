{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro InPrint(environment, this, state, arguments) -%}
    {{ environment.logError(environment, "TODO: In") }}
{%- endmacro %}

{%- macro InStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set In = namespace() %}
    {%- set environment.In = In %}
    {%- do environment.OperatorClass.construct(environment, none, environment.In) %}
    {%- set In.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW_VALUE %}
    {%- set In.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set In.print = InPrint %}
{%- endmacro %}