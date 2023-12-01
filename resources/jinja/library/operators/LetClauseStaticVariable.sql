{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ConversionHandlingFunctions.sql" import ConversionHandlingFunctionsInit %}

{%- macro LetClausePrint(environment, this, state, arguments) -%}
    {%- set dataFormatCarrier = namespace() %}
    {%- do arguments['child']['operator'].getDataSQLFormat(environment, arguments['child']['operator'], state, arguments['child'], dataFormatCarrier) %}

    {%- if dataFormatCarrier.value == environment.DataSQLFormatEnum.RAW_VALUE -%}
        (SELECT {{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }} _val) AS _let_{{ arguments['referenceName']}}
    {%- else -%}
        ({{ arguments['child']['operator'].print(environment, arguments['child']['operator'], state, arguments['child']) }}) AS _let_{{ arguments['referenceName']}}
    {%- endif %}
{%- endmacro %}

{%- macro LetClauseStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {%- do ConversionHandlingFunctionsInit(environment) %}
    {# initialize member variables #}
    {%- set LetClause = namespace() %}
    {%- set environment.LetClause = LetClause %}
    {%- do environment.OperatorClass.construct(environment, none, environment.LetClause) %}
    {%- set LetClause.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set LetClause.defaultDataSQLFormat = environment.DataSQLFormatEnum.INHERITED %}
    {%- set LetClause.defaultAccessType = environment.AccessTypeEnum.INHERITED %}
    {%- set LetClause.print = LetClausePrint %}
{%- endmacro %}