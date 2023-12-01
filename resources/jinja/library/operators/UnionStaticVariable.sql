{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro UnionPrint(environment, this, state, arguments) -%}
    {%- if arguments['mixed'] == 'true' -%}
        SELECT * FROM (SELECT * FROM ({{ environment.printOperatorsFromList(environment, state, arguments['children'], ')) NATURAL FULL JOIN (SELECT * FROM ( ') }}))
    {%- else -%}
        ({{ environment.printOperatorsFromList(environment, state, arguments['children'], ') UNION (') }})
    {%- endif %}
{%- endmacro %}

{%- macro UnionStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Union = namespace() %}
    {%- set environment.Union = Union %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Union) %}
    {%- set Union.defaultDataSQLFormat = environment.DataSQLFormatEnum.QUERY %}
    {%- set Union.defaultDataState = environment.DataStateEnum.TABLE %}
    {%- set Union.defaultAccessType = environment.AccessTypeEnum.SELECT_FROM %}
    {%- set Union.print = UnionPrint %}
{%- endmacro %}