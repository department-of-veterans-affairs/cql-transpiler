
{#
    Environment prerequisites:
        * OperatorClass.sql
        * DataTypeEnum.sql
#}
{% from "jinja_transpilation_libraries/sparksql/default/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{% from "jinja_transpilation_libraries/sparksql/default/ExpressionDefStaticVariable.sql" import ExpressionDefStaticVariableInit %}
{% from "jinja_transpilation_libraries/sparksql/default/SingletonFromStaticVariable.sql" import SingletonFromStaticVariableInit %}
{% from "jinja_transpilation_libraries/sparksql/default/RetrieveStaticVariable.sql" import RetrieveStaticVariableInit %}
{% from "jinja_transpilation_libraries/sparksql/RetrieveCustomOverride.sql" import RetrieveCustomOverrideInit %}

{%- set environment = namespace() %}

{%- macro TestOutputInit(environment) %}
{%-     do ExpressionDefStaticVariableInit(environment) %}
{%-     do SingletonFromStaticVariableInit(environment) %}
{%-     do RetrieveStaticVariableInit(environment) %}
{#{%-     do RetrieveCustomOverrideInit(environment) %}#}
{%- endmacro %}

{%- do TestOutputInit(environment) %}

{% macro TestOutputPatient(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Patient', 'child': { 'operator': environment.SingletonFrom, 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': none, 'codeComparator': none, 'codeProperty': none, 'child': none, 'valueSet': none } } }) }}{% endmacro %}

{{ TestOutputPatient(environment, none) }}