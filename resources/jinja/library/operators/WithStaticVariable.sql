{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro WithPrint(environment, this, state, arguments) -%}
    WHERE (LET {{ environment.OperatorHandler.print(environment, this, state, arguments['child'])}} AS {{ arguments['alias'] }} SELECT {{ environment.OperatorHandler.print(environment, this, state, arguments['suchThat']) }} {{ environment.printSingleValueColumnName(environment) }})
{%- endmacro %}

{%- macro WithStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorHandlerStaticVariableInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do DataTypeEnumInit(environment) %}
    {#- initialize member variables #}
    {%- set With = namespace() %}
    {%- set environment.With = With %}
    {%- do environment.OperatorClass.construct(environment, none, environment.With) %}
    {%- set With.defaultDataType = environment.DataTypeEnum.STATEMENT %}
    {%- set With.print = WithPrint %}
{%- endmacro %}