{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}
{%- from "library/globals/ContextHandlingFunctions.sql" import ContextHandlingFunctionsInit %}

{%- macro WithPrint(environment, this, state, arguments) -%}
    {{ arguments['alias'] }} USING ({{ environment.printIDFromContext(environment, state.context) }}) WHERE {{ arguments['suchThat']['operator'].print(environment, arguments['suchThat']['operator'], state, arguments['suchThat']) }}
{%- endmacro %}

{%- macro WithStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardFunctionsInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {%- do ContextHandlingFunctionsInit(environment) %}
    {#- initialize member variables #}
    {%- set With = namespace() %}
    {%- set environment.With = With %}
    {%- do environment.OperatorClass.construct(environment, none, environment.With) %}
    {%- set With.defaultDataSQLFormat = environment.DataSQLFormatEnum.INHERITED %}
    {%- set With.defaultDataState = environment.DataStateEnum.INHERITED %}
    {%- set With.defaultAccessType = environment.AccessTypeEnum.INHERITED %}
    {%- set With.print = WithPrint %}
{%- endmacro %}