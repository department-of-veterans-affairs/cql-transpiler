{%- from "library/globals/OperatorHandlerStaticVariable.sql" import OperatorHandlerStaticVariableInit %}
{%- from "library/operators/RetrieveStaticVariable.sql" import RetrieveStaticVariableInit %}
{%- from "custom_overrides/ValueSetUtilityMacros.sql" import valuesetCodes %}
{#- Placeholder for dbt function #}
{%- from "jinja_only/mock_functions/MockDBTFunctions.sql" import source %}
{#- Placeholder for dbt function #}
{%- from "jinja_only/mock_functions/MockDBTFunctions.sql" import ref %}
{#- Placeholder for dbt function #}
{%- from "jinja_only/mock_functions/MockDBTFunctions.sql" import env_var %}

{%- macro RetrieveGetDBT(environment, state, valueSet, model, dataType, version, codeProperty="code") -%}
    {%- if dataType == 'patient' %}
        {%- set dataTypeReference = 'common__patient' %}
    {%- else %}
        {%- set dataTypeReference = model ~ "__" ~ dataType ~ "_" ~ version %}
    {%- endif -%}
    /* Retrieve with arguments: ({% if valueSet == none %}{{ [none, model, dataType, version, codeProperty ] }}{% else %}{{ [valueSet['referenceTo']['value'], model, dataType, version, codeProperty ] }}{% endif %}) begins */SELECT _dataType.*, GETDATE() _evaluatedOn, DATE_TRUNC('MM', _ep.measurementPeriod.high) _partitionKey, STRUCT(STRING(_ep.measurementPeriod) key, _ep.measurementPeriod measurementPeriod) _parameters FROM {{ source("fhirlake", dataTypeReference) }} _dataType CROSS JOIN {{ ref("system__evaluation_period") }} _ep{% if valueSet %} WHERE EXISTS (SELECT * FROM (SELECT explode(codes) codes FROM ({{ valuesetCodes(environment, state, valueSet, asOfDate) }})) AS _vs WHERE {{ "_dataType." ~ codeProperty }}.code = _vs.codes.code AND {{ "_dataType." ~ codeProperty }}.system = _vs.codes.system){% endif %}/* Retrieve ends */
{%- endmacro %}

{%- macro RetrievePrintCustomOverride(environment, this, state, arguments) -%}
    {%- if arguments['resultTypeLabel'] %}
        {%- set resultTypeLabel = arguments['resultTypeLabel'] %}
    {%- else %}
        {%- set resultTypeLabel = arguments['templateId'] %}
    {%- endif -%}
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
    {#- initialize member variables #}
    {%- set Retrieve = environment.Retrieve %}
    {%- set Retrieve.print = RetrievePrintCustomOverride %}
{%- endmacro %}