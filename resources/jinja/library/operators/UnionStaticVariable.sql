{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro UnionPrint(environment, this, state, arguments) -%}
    {%- if arguments['mixed'] == 'true' -%}
        {#- TODO: have full joins happnen on some sort of primary key, if possible -#}
        SELECT * FROM (SELECT * FROM {{ environment.printOperatorsFromList(environment, state, arguments['children'], ') FULL JOIN (SELECT * FROM ') }}) ON 1=0
    {%- else -%}
        (SELECT * FROM {{ environment.printOperatorsFromList(environment, state, arguments['children'], ') UNION (SELECT * FROM ') }})
    {%- endif %}
{%- endmacro %}

{%- macro UnionStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set Union = namespace() %}
    {%- set environment.Union = Union %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Union) %}
    {%- set Union.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set Union.print = UnionPrint %}
{%- endmacro %}