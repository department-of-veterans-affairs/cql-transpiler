{#- Placeholder for dbt function #}
{%- from "jinja_only/mock_functions/MockDBTFunctions.sql" import source %}
{#- Placeholder for dbt function #}
{%- from "jinja_only/mock_functions/MockDBTFunctions.sql" import ref %}
{#- Placeholder for dbt function #}
{%- from "jinja_only/mock_functions/MockDBTFunctions.sql" import env_var %}

{%- macro systemValueset() -%}
    WITH cy2024 AS (SELECT _c1 `oid`, _c0 `name`, _c2 `version`, _c8 `code`, _c9 `display`, _c10 `codeSystem`, _c11 `codeSystemOid`FROM CSV.`/mnt/zones/subenv/{{ env_var('WORKGROUP_SUBENV')|lower }}/specification/valueset/vsac/ec_eh_oqr_unique_vs_20230504.csv.gz`), all AS (SELECT t.oid, t.name, t.version, STRUCT(t.code, t.display, cs.url system) code FROM cy2024 t LEFT JOIN {{ source("fhirlake", "terminology__codesystem") }} cs ON CONCAT('urn:oid:', t.codeSystemOid) = cs.identifier[0].value) SELECT oid, FIRST(name) name, version, COLLECT_LIST(code) codes FROM all GROUP BY oid, version
{%- endmacro %}

{%- macro valuesetCodes(environment, state, valueSet, asOfDate) -%}
    SELECT codes FROM ({{ systemValueset() }}) WHERE oid = "{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, valueSet) }}"{% if asOfDate %} AND version <= "{{ asOfDate }}"{% endif %} ORDER BY version DESC LIMIT 1
{%- endmacro %}