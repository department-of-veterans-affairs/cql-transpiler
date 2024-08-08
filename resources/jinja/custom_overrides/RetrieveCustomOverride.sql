{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * RetrieveStaticVariable.sql
#}
{%- from "ValueSetUtilityMacros.sql" import valuesetCodes %}
{%- from "jinja/library/operators/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "jinja/library/operators/RetrieveStaticVariable.sql" import RetrieveStaticVariableInit %}
{%- from "jinja/library/operators/RetrieveStaticVariable.sql" import RetrieveStaticVariableInit %}

{%- macro RetrieveGetDBT(environment, state, valueSet, model, dataType, version, codeProperty="code") -%}
  {%- if dataType == 'patient' %}
    {%- set dataTypeReference = 'common__patient' %}
  {%- else %}
    {%- set dataTypeReference = model ~ "__" ~ dataType ~ "_" ~ version %}
  {%- endif %}
  SELECT _dataType.*, {# Apply system attributes during the retrieve so they're present within derived calculations. #}GETDATE() _evaluatedOn, DATE_TRUNC('MM', _ep.measurementPeriod.high) _partitionKey, STRUCT(STRING(_ep.measurementPeriod) key, _ep.measurementPeriod measurementPeriod) _parameters FROM {{ source("fhirlake", dataTypeReference) }} _dataType {# Link to evaluation period which represents #}CROSS JOIN {{ ref("system__evaluation_period") }} _ep
  {%- if valueSet %} WHERE EXISTS (SELECT * FROM (SELECT explode(codes) codes FROM ({{ valuesetCodes(environment, state, valueSet, asOfDate) }})) AS _vs WHERE {{ "_dataType." ~ codeProperty }}.code = _vs.codes.code AND {{ "_dataType." ~ codeProperty }}.system = _vs.codes.system)
  {%- endif %}
{%- endmacro %}

{%- macro RetrievePrintCustomOverride(environment, this, state, arguments) -%}
{%-   if arguments['resultTypeLabel'] %}
{%-     set resultTypeLabel = arguments['resultTypeLabel'] %}
{%-   else %}
{%-     set resultTypeLabel = arguments['templateId'] %}
{%-   endif %}
{{ RetrieveGetDBT(
  environment,
  state,
  arguments['valueSet'],
  arguments['modelType'].split(':')[2]|lower,
  resultTypeLabel|lower|replace(" ", "_")|replace(",", ""),
  arguments['modelType'].split(':')[3]|replace("_", ""),
  arguments['codeProperty']
) }}
{%- endmacro %}

{%- macro RetrieveCustomOverrideInit(environment) %}
  {#- initialize prerequisites #}
  {%- do OperatorHandlerStaticVariableInit(environment) %}
  {%- do RetrieveStaticVariableInit(environment) %}
  {# initialize member variables #}
  {%- set Retrieve = environment.Retrieve %}
  {%- set Retrieve.print = RetrievePrintCustomOverride %}
{%- endmacro %}