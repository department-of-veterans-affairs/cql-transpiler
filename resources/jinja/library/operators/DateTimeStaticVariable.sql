{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro DateTimePrint(environment, this, state, arguments) -%}
    TODATETIMEOFFSET(DATETIMEFROMPARTS({{ arguments['year']['operator'].print(environment, arguments['year']['operator'], state, arguments['year']) }}, {{ arguments['month']['operator'].print(environment, arguments['month']['operator'], state, arguments['month']) }}, {{ arguments['day']['operator'].print(environment, arguments['day']['operator'], state, arguments['day']) }}, {{ arguments['hour']['operator'].print(environment, arguments['hour']['operator'], state, arguments['hour']) }}, {{ arguments['minute']['operator'].print(environment, arguments['minute']['operator'], state, arguments['minute']) }}, {{ arguments['second']['operator'].print(environment, arguments['second']['operator'], state, arguments['second']) }}, {{ arguments['millisecond']['operator'].print(environment, arguments['millisecond']['operator'], state, arguments['millisecond']) }}), {{ arguments['timezoneOffset']['operator'].print(environment, arguments['timezoneOffset']['operator'], state, arguments['timezoneOffset']) }})
{%- endmacro %}

{%- macro DateTimeStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set DateTime = namespace() %}
    {%- set environment.DateTime = DateTime %}
    {%- do environment.OperatorClass.construct(environment, none, environment.DateTime) %}
    {%- set DateTime.defaultDataSQLFormat = environment.DataSQLFormatEnum.RAW %}
    {%- set DateTime.defaultDataState = environment.DataStateEnum.SIMPLE %}
    {%- set DateTime.print = DateTimePrint %}
{%- endmacro %}