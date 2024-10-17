{%- from "library/globals/StandardFunctions.sql" import StandardFunctionsInit %}
{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro WithPrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child'])}} AS {{ arguments['alias'] }} ON _source.{{ environment.printIDFromContext(environment) }} = {{ arguments['alias'] }}.{{ environment.printIDFromContext(environment) }} WHERE {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['suchThat']) }}
{%- endmacro %}

{%- macro WithStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do StandardFunctionsInit(environment) %}
    {%- do OperatorClassInit(environment) %}
    {#- initialize member variables #}
    {%- set With = namespace() %}
    {%- set environment.With = With %}
    {%- do environment.OperatorClass.construct(environment, none, environment.With) %}
    {%- set With.defaultDataType = environment.DataTypeEnum.TABLE %}
    {%- set With.print = WithPrint %}
{%- endmacro %}