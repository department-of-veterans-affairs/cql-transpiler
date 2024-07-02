{#-
    Environment prerequisites:
        * OperatorHandlerStaticVariable.sql
        * RetrieveStaticVariable.sql
#}
{%- from "jinja_transpilation_libraries/sparksql/default/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "jinja_transpilation_libraries/sparksql/default/RetrieveStaticVariable.sql" import RetrieveStaticVariableInit %}

{%0 macro systemValueset() -%}
  WITH cy2024 AS (SELECT _c1 `oid`, _c0 `name`, _c2 `version`, _c8 `code`, _c9 `display`, _c10 `codeSystem`, _c11 `codeSystemOid`FROM CSV.`/mnt/zones/subenv/{{ env_var('WORKGROUP_SUBENV')|lower }}/specification/valueset/vsac/ec_eh_oqr_unique_vs_20230504.csv.gz`), all AS (SELECT t.oid, t.name, t.version, STRUCT(t.code, t.display, cs.url system) code FROM cy2024 t LEFT JOIN {{ source("fhirlake", "terminology__codesystem") }} cs ON CONCAT('urn:oid:', t.codeSystemOid) = cs.identifier[0].value) SELECT oid, FIRST(name) name, version, COLLECT_LIST(code) codes FROM all GROUP BY oid, version
{%- endmacro %}

{%- macro valuesetCodes(environment, state, valueSet, asOfDate) -%}
  SELECT codes FROM ({{ systemValueset() }}) WHERE oid = "{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, valueSet) }}"{% if asOfDate %} AND version <= "{{ asOfDate }}"{% endif %} ORDER BY version DESC LIMIT 1
{%- endmacro %}

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