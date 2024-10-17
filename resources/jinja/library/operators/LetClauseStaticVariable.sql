{%- from "library/globals/OperatorClass.sql" import OperatorClassInit %}

{%- macro LetClauseAllowsSelectFromAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsSelectFromAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro LetClauseAllowsDotPropertyAccessType(environment, this, carrier, state, arguments) %}
    {%- do arguments['child']['operator'].allowsDotPropertyAccessType(environment, arguments['child']['operator'], carrier, state, arguments['child']) %}
{%- endmacro %}

{%- macro LetClausePrint(environment, this, state, arguments) -%}
    {{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, arguments['child']) }} AS {{ arguments['referenceName'] }}
{%- endmacro %}

{%- macro LetClauseStaticVariableInit(environment) %}
    {#- initialize prerequisites #}
    {%- do OperatorClassInit(environment) %}
    {# initialize member variables #}
    {%- set LetClause = namespace() %}
    {%- set environment.LetClause = LetClause %}
    {%- do environment.OperatorClass.construct(environment, none, environment.LetClause) %}
    {%- set LetClause.allowsSelectFromAccessType = LetClauseAllowsSelectFromAccessType %}
    {%- set LetClause.allowsDotPropertyAccessType = LetClauseAllowsDotPropertyAccessType %}
    {%- set LetClause.defaultDataType = environment.DataTypeEnum.INHERITED %}
    {%- set LetClause.print = LetClausePrint %}
{%- endmacro %}