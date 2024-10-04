{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}
{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/DataTypeEnum.sql" import DataTypeEnumInit %}

{%- macro WithPrint(environment, this, state, arguments) -%}
    {#- TODO -#}
    /* SEMI JOIN {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child'])}} AS {{ arguments['alias'] }} ON ... {{ arguments['alias'] }}.{{ environment.printSingleValueColumnName(environment) }} WHERE {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['suchThat']) }} */
{%- endmacro %}

{%- macro WithStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardFunctionsInit(environment) %}
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