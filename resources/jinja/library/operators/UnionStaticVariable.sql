{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}


{%- macro UnionPrint(environment, this, state, arguments) -%}
    {%- if arguments['mixed'] == 'true' -%}
        /* todo -- Union -- unions with mixed arguments should happen on a primary key */SELECT * FROM (SELECT * FROM ({{ environment.printOperatorsFromList(environment, state, arguments['children'], ')) FULL JOIN (SELECT * FROM ( ') }})) ON 1=0
    {%- else -%}
        (SELECT * FROM {{ environment.printOperatorsFromList(environment, state, arguments['children'], ') UNION (SELECT * FROM ') }})
    {%- endif %}
{%- endmacro %}

{%- macro UnionStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set Union = namespace() %}
    {%- set environment.Union = Union %}
    {%- do environment.OperatorClass.construct(environment, none, environment.Union) %}
    {%- set Union.allowsSelectFromAccessTypeByDefault = true %}
    {%- set Union.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set Union.print = UnionPrint %}
{%- endmacro %}