{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import printOperator %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import UsingDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ValueSetDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ParameterDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import IntervalTypeSpecifier %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import NamedTypeSpecifier %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ContextDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ExpressionDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import SingletonFrom %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Retrieve %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ValueSetRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import FunctionDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Disabled %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import DifferenceBetween %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Start %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import OperandRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import End %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import OperandDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Query %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import AliasedQuerySource %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import And %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import LessOrEqual %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import FunctionRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Property %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Literal %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import InInterval %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ParameterRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Interval %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import DateFrom %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import LetClause %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Subtract %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Quantity %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Not %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import IsNull %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import SortClause %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ByExpression %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import IdentifierRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ReturnClause %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import If %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import QueryLetRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Flatten %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import List %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ListTypeSpecifier %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Unsupported %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import InValueSet %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Coalesce %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import As %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Null %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Or %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Equal %}
/*
	// MATGlobalCommonFunctions lines [5:1-5:82]
	valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292' 
*/
{% set Emergency_Department_Visit = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292' %}
/*
	// MATGlobalCommonFunctions lines [6:1-6:71]
	valueset "Encounter Inpatient": 'urn:oid:2.16.840.1.113883.3.666.5.307' 
*/
{% set Encounter_Inpatient = 'urn:oid:2.16.840.1.113883.3.666.5.307' %}
/*
	// MATGlobalCommonFunctions lines [7:1-7:72]
	valueset "Intensive Care Unit": 'urn:oid:2.16.840.1.113762.1.4.1029.206' 
*/
{% set Intensive_Care_Unit = 'urn:oid:2.16.840.1.113762.1.4.1029.206' %}
/*
	// MATGlobalCommonFunctions lines [8:1-8:73]
	valueset "Observation Services": 'urn:oid:2.16.840.1.113762.1.4.1111.143' 
*/
{% set Observation_Services = 'urn:oid:2.16.840.1.113762.1.4.1111.143' %}
/*
	// MATGlobalCommonFunctions lines [9:1-9:78]
	valueset "Outpatient Surgery Service": 'urn:oid:2.16.840.1.113762.1.4.1110.38' 
*/
{% set Outpatient_Surgery_Service = 'urn:oid:2.16.840.1.113762.1.4.1110.38' %}
/*
	// MATGlobalCommonFunctions lines [13:1-13:15]
	context Patient
*/
{% macro MATGlobalCommonFunctionsPatient(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Patient', 'child': { 'operator': SingletonFrom, 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': none, 'codeComparator': none, 'codeProperty': none, 'child': none, 'valueSet': none } } }) }}{% endmacro %}
{{ MATGlobalCommonFunctionsPatient(none) }}
/*
	// MATGlobalCommonFunctions lines [15:1-16:56]
	define "ED Encounter":
	  ["Encounter, Performed": "Emergency Department Visit"]
*/
{% macro MATGlobalCommonFunctionsED_Encounter(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'ED Encounter', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': Emergency_Department_Visit } } }) }}{% endmacro %}
{{ MATGlobalCommonFunctionsED_Encounter(none) }}
/*
	// MATGlobalCommonFunctions lines [32:1-33:60]
	define function "LengthInDays"(Value Interval<DateTime> ):
	  difference in days between start of Value and end of Value
*/
{% macro MATGlobalCommonFunctionsLengthInDays(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'LengthInDays', 'child': { 'operator': DifferenceBetween, 'left': { 'operator': Start, 'child': { 'operator': OperandRef, 'reference': 'Value' } }, 'right': { 'operator': End, 'child': { 'operator': OperandRef, 'reference': 'Value' } } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Value', 'children': [{ 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [18:1-21:83]
	define "Inpatient Encounter":
	  ["Encounter, Performed": "Encounter Inpatient"] EncounterInpatient
	    where "LengthInDays"(EncounterInpatient.relevantPeriod)<= 120
	      and EncounterInpatient.relevantPeriod ends during day of "Measurement Period"
*/
{% macro MATGlobalCommonFunctionsInpatient_Encounter(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Inpatient Encounter', 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': LessOrEqual, 'left': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsLengthInDays, 'children': [{ 'operator': Property, 'scope': 'EncounterInpatient', 'path': 'relevantPeriod', 'child': none }] }, 'right': { 'operator': Literal, 'type': 'Integer', 'value': '120' } }, 'right': { 'operator': InInterval, 'left': { 'operator': End, 'child': { 'operator': Property, 'scope': 'EncounterInpatient', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': ParameterRef, 'name': 'Measurement_Period' } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'EncounterInpatient', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': Encounter_Inpatient } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ MATGlobalCommonFunctionsInpatient_Encounter(none) }}
/*
	// MATGlobalCommonFunctions lines [28:1-29:62]
	define function "ToDateInterval"(period Interval<DateTime> ):
	  Interval[date from start of period, date from end of period]
*/
{% macro MATGlobalCommonFunctionsToDateInterval(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'ToDateInterval', 'child': { 'operator': Interval, 'high': { 'operator': DateFrom, 'child': { 'operator': End, 'child': { 'operator': OperandRef, 'reference': 'period' } } }, 'highClosedExpression': none, 'low': { 'operator': DateFrom, 'child': { 'operator': Start, 'child': { 'operator': OperandRef, 'reference': 'period' } } }, 'lowClosedExpression': none }, 'typeSpecifier': { 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}Date', 'children': [] }] }, 'operators': [{ 'operator': OperandDef, 'name': 'period', 'children': [{ 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [36:1-44:71]
	define function "HospitalizationLocations"(Encounter "Encounter, Performed" ):
	  Encounter Visit
	  	let EDVisit: Last(["Encounter, Performed": "Emergency Department Visit"] LastED
	  			where LastED.relevantPeriod ends 1 hour or less on or before start of Visit.relevantPeriod
	  			sort by 
	  			end of relevantPeriod
	  	)
	  	return if EDVisit is null then Visit.facilityLocations 
	  		else flatten { EDVisit.facilityLocations, Visit.facilityLocations }
*/
{% macro MATGlobalCommonFunctionsHospitalizationLocations(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalizationLocations', 'child': { 'operator': Query, 'where': none, 'returnClause': { 'operator': ReturnClause, 'children': [{ 'operator': If, 'condition': { 'operator': IsNull, 'child': { 'operator': QueryLetRef, 'name': 'EDVisit' } }, 'then': { 'operator': Property, 'scope': 'Visit', 'path': 'facilityLocations', 'child': none }, 'else': { 'operator': Flatten, 'child': { 'operator': List, 'children': [{ 'operator': Property, 'scope': none, 'path': 'facilityLocations', 'child': { 'operator': QueryLetRef, 'name': 'EDVisit' } }, { 'operator': Property, 'scope': 'Visit', 'path': 'facilityLocations', 'child': none }] } } }] }, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'Visit', 'child': { 'operator': OperandRef, 'reference': 'Encounter' } }], 'letClauseList': [{ 'operator': LetClause, 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': InInterval, 'left': { 'operator': End, 'child': { 'operator': Property, 'scope': 'LastED', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Interval, 'high': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'highClosedExpression': none, 'low': { 'operator': Subtract, 'left': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Quantity, 'unit': 'hour', 'value': '1' } }, 'lowClosedExpression': none } }, 'right': { 'operator': Not, 'child': { 'operator': IsNull, 'child': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } } } } }, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': End, 'child': { 'operator': IdentifierRef, 'name': 'relevantPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'LastED', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': Emergency_Department_Visit } } }], 'letClauseList': [] } }] }, 'typeSpecifier': { 'operator': ListTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}FacilityLocation', 'children': [] }] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [47:1-51:18]
	define function "EmergencyDepartmentArrivalTime"(Encounter "Encounter, Performed" ):
	  start of First(("HospitalizationLocations"(Encounter))HospitalLocation
	  		where HospitalLocation.code in "Emergency Department Visit"
	  		sort by start of locationPeriod
	  ).locationPeriod
*/
{% macro MATGlobalCommonFunctionsEmergencyDepartmentArrivalTime(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'EmergencyDepartmentArrivalTime', 'child': { 'operator': Start, 'child': { 'operator': Property, 'scope': none, 'path': 'locationPeriod', 'child': { 'operator': Unsupported, 'unsupportedOperator': 'First', 'children': [{ 'operator': Query, 'where': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': Emergency_Department_Visit }, 'valueSetExpression': none }, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': Start, 'child': { 'operator': IdentifierRef, 'name': 'locationPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'HospitalLocation', 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalizationLocations, 'children': [{ 'operator': OperandRef, 'reference': 'Encounter' }] } }], 'letClauseList': [] }] } } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [64:1-72:31]
	define function "Hospitalization"(Encounter "Encounter, Performed" ):
	  Encounter Visit
	  	let EDVisit: Last(["Encounter, Performed": "Emergency Department Visit"] LastED
	  			where LastED.relevantPeriod ends 1 hour or less on or before start of Visit.relevantPeriod
	  			sort by 
	  			end of relevantPeriod
	  	)
	  	return Interval[Coalesce(start of EDVisit.relevantPeriod, start of Visit.relevantPeriod), 
	  	end of Visit.relevantPeriod]
*/
{% macro MATGlobalCommonFunctionsHospitalization(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'Hospitalization', 'child': { 'operator': Query, 'where': none, 'returnClause': { 'operator': ReturnClause, 'children': [{ 'operator': Interval, 'high': { 'operator': End, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'highClosedExpression': none, 'low': { 'operator': Coalesce, 'children': [{ 'operator': Start, 'child': { 'operator': Property, 'scope': none, 'path': 'relevantPeriod', 'child': { 'operator': QueryLetRef, 'name': 'EDVisit' } } }, { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }] }, 'lowClosedExpression': none }] }, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'Visit', 'child': { 'operator': OperandRef, 'reference': 'Encounter' } }], 'letClauseList': [{ 'operator': LetClause, 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': InInterval, 'left': { 'operator': End, 'child': { 'operator': Property, 'scope': 'LastED', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Interval, 'high': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'highClosedExpression': none, 'low': { 'operator': Subtract, 'left': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Quantity, 'unit': 'hour', 'value': '1' } }, 'lowClosedExpression': none } }, 'right': { 'operator': Not, 'child': { 'operator': IsNull, 'child': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } } } } }, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': End, 'child': { 'operator': IdentifierRef, 'name': 'relevantPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'LastED', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': Emergency_Department_Visit } } }], 'letClauseList': [] } }] }, 'typeSpecifier': { 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [54:1-55:39]
	define function "HospitalAdmissionTime"(Encounter "Encounter, Performed" ):
	  start of "Hospitalization"(Encounter)
*/
{% macro MATGlobalCommonFunctionsHospitalAdmissionTime(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalAdmissionTime', 'child': { 'operator': Start, 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalization, 'children': [{ 'operator': OperandRef, 'reference': 'Encounter' }] } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [58:1-61:18]
	define function "HospitalArrivalTime"(Encounter "Encounter, Performed" ):
	  start of First(("HospitalizationLocations"(Encounter))HospitalLocation
	  		sort by start of locationPeriod
	  ).locationPeriod
*/
{% macro MATGlobalCommonFunctionsHospitalArrivalTime(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalArrivalTime', 'child': { 'operator': Start, 'child': { 'operator': Property, 'scope': none, 'path': 'locationPeriod', 'child': { 'operator': Unsupported, 'unsupportedOperator': 'First', 'children': [{ 'operator': Query, 'where': none, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': Start, 'child': { 'operator': IdentifierRef, 'name': 'locationPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'HospitalLocation', 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalizationLocations, 'children': [{ 'operator': OperandRef, 'reference': 'Encounter' }] } }], 'letClauseList': [] }] } } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [75:1-76:44]
	define function "HospitalizationLengthofStay"(Encounter "Encounter, Performed" ):
	  LengthInDays("Hospitalization"(Encounter))
*/
{% macro MATGlobalCommonFunctionsHospitalizationLengthofStay(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalizationLengthofStay', 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsLengthInDays, 'children': [{ 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalization, 'children': [{ 'operator': OperandRef, 'reference': 'Encounter' }] }] }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [79:1-82:18]
	define function "HospitalDepartureTime"(Encounter "Encounter, Performed" ):
	  end of Last(("HospitalizationLocations"(Encounter))HospitalLocation
	  		sort by start of locationPeriod
	  ).locationPeriod
*/
{% macro MATGlobalCommonFunctionsHospitalDepartureTime(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalDepartureTime', 'child': { 'operator': End, 'child': { 'operator': Property, 'scope': none, 'path': 'locationPeriod', 'child': { 'operator': Unsupported, 'unsupportedOperator': 'Last', 'children': [{ 'operator': Query, 'where': none, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': Start, 'child': { 'operator': IdentifierRef, 'name': 'locationPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'HospitalLocation', 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalizationLocations, 'children': [{ 'operator': OperandRef, 'reference': 'Encounter' }] } }], 'letClauseList': [] }] } } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [85:1-86:33]
	define function "HospitalDischargeTime"(Encounter "Encounter, Performed" ):
	  end of Encounter.relevantPeriod
*/
{% macro MATGlobalCommonFunctionsHospitalDischargeTime(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalDischargeTime', 'child': { 'operator': End, 'child': { 'operator': Property, 'scope': none, 'path': 'relevantPeriod', 'child': { 'operator': OperandRef, 'reference': 'Encounter' } } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [89:1-109:31]
	define function "HospitalizationWithObservationAndOutpatientSurgeryService"(Encounter "Encounter, Performed" ):
	  Encounter Visit
	  	let ObsVisit: Last(["Encounter, Performed": "Observation Services"] LastObs
	  			where LastObs.relevantPeriod ends 1 hour or less on or before start of Visit.relevantPeriod
	  			sort by 
	  			end of relevantPeriod
	  	),
	  	VisitStart: Coalesce(start of ObsVisit.relevantPeriod, start of Visit.relevantPeriod),
	  	EDVisit: Last(["Encounter, Performed": "Emergency Department Visit"] LastED
	  			where LastED.relevantPeriod ends 1 hour or less on or before VisitStart
	  			sort by 
	  			end of relevantPeriod
	  	),
	  	VisitStartWithED: Coalesce(start of EDVisit.relevantPeriod, VisitStart),
	  	OutpatientSurgeryVisit: Last(["Encounter, Performed": "Outpatient Surgery Service"] LastSurgeryOP
	  			where LastSurgeryOP.relevantPeriod ends 1 hour or less on or before VisitStartWithED
	  			sort by 
	  			end of relevantPeriod
	  	)
	  	return Interval[Coalesce(start of OutpatientSurgeryVisit.relevantPeriod, VisitStartWithED), 
	  	end of Visit.relevantPeriod]
*/
{% macro MATGlobalCommonFunctionsHospitalizationWithObservationAndOutpatientSurgeryService(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalizationWithObservationAndOutpatientSurgeryService', 'child': { 'operator': Query, 'where': none, 'returnClause': { 'operator': ReturnClause, 'children': [{ 'operator': Interval, 'high': { 'operator': End, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'highClosedExpression': none, 'low': { 'operator': Coalesce, 'children': [{ 'operator': Start, 'child': { 'operator': Property, 'scope': none, 'path': 'relevantPeriod', 'child': { 'operator': QueryLetRef, 'name': 'OutpatientSurgeryVisit' } } }, { 'operator': QueryLetRef, 'name': 'VisitStartWithED' }] }, 'lowClosedExpression': none }] }, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'Visit', 'child': { 'operator': OperandRef, 'reference': 'Encounter' } }, { 'operator': Coalesce, 'children': [{ 'operator': Start, 'child': { 'operator': Property, 'scope': none, 'path': 'relevantPeriod', 'child': { 'operator': QueryLetRef, 'name': 'ObsVisit' } } }, { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }] }, { 'operator': Coalesce, 'children': [{ 'operator': Start, 'child': { 'operator': Property, 'scope': none, 'path': 'relevantPeriod', 'child': { 'operator': QueryLetRef, 'name': 'EDVisit' } } }, { 'operator': QueryLetRef, 'name': 'VisitStart' }] }], 'letClauseList': [{ 'operator': LetClause, 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': InInterval, 'left': { 'operator': End, 'child': { 'operator': Property, 'scope': 'LastObs', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Interval, 'high': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'highClosedExpression': none, 'low': { 'operator': Subtract, 'left': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Quantity, 'unit': 'hour', 'value': '1' } }, 'lowClosedExpression': none } }, 'right': { 'operator': Not, 'child': { 'operator': IsNull, 'child': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } } } } }, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': End, 'child': { 'operator': IdentifierRef, 'name': 'relevantPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'LastObs', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': Observation_Services } } }], 'letClauseList': [] } }, { 'operator': LetClause, 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': InInterval, 'left': { 'operator': End, 'child': { 'operator': Property, 'scope': 'LastED', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Interval, 'high': { 'operator': QueryLetRef, 'name': 'VisitStart' }, 'highClosedExpression': none, 'low': { 'operator': Subtract, 'left': { 'operator': QueryLetRef, 'name': 'VisitStart' }, 'right': { 'operator': Quantity, 'unit': 'hour', 'value': '1' } }, 'lowClosedExpression': none } }, 'right': { 'operator': Not, 'child': { 'operator': IsNull, 'child': { 'operator': QueryLetRef, 'name': 'VisitStart' } } } }, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': End, 'child': { 'operator': IdentifierRef, 'name': 'relevantPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'LastED', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': Emergency_Department_Visit } } }], 'letClauseList': [] } }, { 'operator': LetClause, 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': InInterval, 'left': { 'operator': End, 'child': { 'operator': Property, 'scope': 'LastSurgeryOP', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Interval, 'high': { 'operator': QueryLetRef, 'name': 'VisitStartWithED' }, 'highClosedExpression': none, 'low': { 'operator': Subtract, 'left': { 'operator': QueryLetRef, 'name': 'VisitStartWithED' }, 'right': { 'operator': Quantity, 'unit': 'hour', 'value': '1' } }, 'lowClosedExpression': none } }, 'right': { 'operator': Not, 'child': { 'operator': IsNull, 'child': { 'operator': QueryLetRef, 'name': 'VisitStartWithED' } } } }, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': End, 'child': { 'operator': IdentifierRef, 'name': 'relevantPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'LastSurgeryOP', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': Outpatient_Surgery_Service } } }], 'letClauseList': [] } }] }, 'typeSpecifier': { 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [112:1-126:31]
	define function "HospitalizationWithObservation"(Encounter "Encounter, Performed" ):
	  Encounter Visit
	  	let ObsVisit: Last(["Encounter, Performed": "Observation Services"] LastObs
	  			where LastObs.relevantPeriod ends 1 hour or less on or before start of Visit.relevantPeriod
	  			sort by 
	  			end of relevantPeriod
	  	),
	  	VisitStart: Coalesce(start of ObsVisit.relevantPeriod, start of Visit.relevantPeriod),
	  	EDVisit: Last(["Encounter, Performed": "Emergency Department Visit"] LastED
	  			where LastED.relevantPeriod ends 1 hour or less on or before VisitStart
	  			sort by 
	  			end of relevantPeriod
	  	)
	  	return Interval[Coalesce(start of EDVisit.relevantPeriod, VisitStart), 
	  	end of Visit.relevantPeriod]
*/
{% macro MATGlobalCommonFunctionsHospitalizationWithObservation(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalizationWithObservation', 'child': { 'operator': Query, 'where': none, 'returnClause': { 'operator': ReturnClause, 'children': [{ 'operator': Interval, 'high': { 'operator': End, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'highClosedExpression': none, 'low': { 'operator': Coalesce, 'children': [{ 'operator': Start, 'child': { 'operator': Property, 'scope': none, 'path': 'relevantPeriod', 'child': { 'operator': QueryLetRef, 'name': 'EDVisit' } } }, { 'operator': QueryLetRef, 'name': 'VisitStart' }] }, 'lowClosedExpression': none }] }, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'Visit', 'child': { 'operator': OperandRef, 'reference': 'Encounter' } }, { 'operator': Coalesce, 'children': [{ 'operator': Start, 'child': { 'operator': Property, 'scope': none, 'path': 'relevantPeriod', 'child': { 'operator': QueryLetRef, 'name': 'ObsVisit' } } }, { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }] }], 'letClauseList': [{ 'operator': LetClause, 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': InInterval, 'left': { 'operator': End, 'child': { 'operator': Property, 'scope': 'LastObs', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Interval, 'high': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'highClosedExpression': none, 'low': { 'operator': Subtract, 'left': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Quantity, 'unit': 'hour', 'value': '1' } }, 'lowClosedExpression': none } }, 'right': { 'operator': Not, 'child': { 'operator': IsNull, 'child': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'Visit', 'path': 'relevantPeriod', 'child': none } } } } }, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': End, 'child': { 'operator': IdentifierRef, 'name': 'relevantPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'LastObs', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': Observation_Services } } }], 'letClauseList': [] } }, { 'operator': LetClause, 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': InInterval, 'left': { 'operator': End, 'child': { 'operator': Property, 'scope': 'LastED', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': Interval, 'high': { 'operator': QueryLetRef, 'name': 'VisitStart' }, 'highClosedExpression': none, 'low': { 'operator': Subtract, 'left': { 'operator': QueryLetRef, 'name': 'VisitStart' }, 'right': { 'operator': Quantity, 'unit': 'hour', 'value': '1' } }, 'lowClosedExpression': none } }, 'right': { 'operator': Not, 'child': { 'operator': IsNull, 'child': { 'operator': QueryLetRef, 'name': 'VisitStart' } } } }, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': End, 'child': { 'operator': IdentifierRef, 'name': 'relevantPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'LastED', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': Emergency_Department_Visit } } }], 'letClauseList': [] } }] }, 'typeSpecifier': { 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [129:1-130:61]
	define function "HospitalizationWithObservationLengthofStay"(Encounter "Encounter, Performed" ):
	  "LengthInDays"("HospitalizationWithObservation"(Encounter))
*/
{% macro MATGlobalCommonFunctionsHospitalizationWithObservationLengthofStay(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalizationWithObservationLengthofStay', 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsLengthInDays, 'children': [{ 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalizationWithObservation, 'children': [{ 'operator': OperandRef, 'reference': 'Encounter' }] }] }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [133:1-138:3]
	define function "FirstInpatientIntensiveCareUnit"(Encounter "Encounter, Performed" ):
	  First((Encounter.facilityLocations)HospitalLocation
	      where HospitalLocation.code in "Intensive Care Unit"
	        and HospitalLocation.locationPeriod during Encounter.relevantPeriod
	      sort by start of locationPeriod
	  )
*/
{% macro MATGlobalCommonFunctionsFirstInpatientIntensiveCareUnit(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'FirstInpatientIntensiveCareUnit', 'child': { 'operator': Unsupported, 'unsupportedOperator': 'First', 'children': [{ 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': Intensive_Care_Unit }, 'valueSetExpression': none }, 'right': { 'operator': Unsupported, 'unsupportedOperator': 'IncludedIn', 'children': [{ 'operator': Property, 'scope': 'HospitalLocation', 'path': 'locationPeriod', 'child': none }, { 'operator': Property, 'scope': none, 'path': 'relevantPeriod', 'child': { 'operator': OperandRef, 'reference': 'Encounter' } }] } }, 'returnClause': none, 'sortClause': { 'operator': SortClause, 'children': [{ 'operator': ByExpression, 'direction': 'ASC', 'child': { 'operator': Start, 'child': { 'operator': IdentifierRef, 'name': 'locationPeriod', 'children': [] } } }] }, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'HospitalLocation', 'child': { 'operator': Property, 'scope': none, 'path': 'facilityLocations', 'child': { 'operator': OperandRef, 'reference': 'Encounter' } } }], 'letClauseList': [] }] }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [141:1-144:35]
	define function "NormalizeInterval"(pointInTime DateTime, period Interval<DateTime> ):
	  if pointInTime is not null then Interval[pointInTime, pointInTime]
	    else if period is not null then period 
	    else null as Interval<DateTime>
*/
{% macro MATGlobalCommonFunctionsNormalizeInterval(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'NormalizeInterval', 'child': { 'operator': If, 'condition': { 'operator': Not, 'child': { 'operator': IsNull, 'child': { 'operator': OperandRef, 'reference': 'pointInTime' } } }, 'then': { 'operator': Interval, 'high': { 'operator': OperandRef, 'reference': 'pointInTime' }, 'highClosedExpression': none, 'low': { 'operator': OperandRef, 'reference': 'pointInTime' }, 'lowClosedExpression': none }, 'else': { 'operator': If, 'condition': { 'operator': Not, 'child': { 'operator': IsNull, 'child': { 'operator': OperandRef, 'reference': 'period' } } }, 'then': { 'operator': OperandRef, 'reference': 'period' }, 'else': { 'operator': As, 'child': { 'operator': Null }, 'typeSpecifier': { 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] } } } }, 'typeSpecifier': { 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, 'operators': [{ 'operator': OperandDef, 'name': 'pointInTime', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, { 'operator': OperandDef, 'name': 'period', 'children': [{ 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [147:1-150:3]
	define function "HasStart"(period Interval<DateTime> ):
	  not ( start of period is null
	      or start of period = minimum DateTime
	  )
*/
{% macro MATGlobalCommonFunctionsHasStart(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HasStart', 'child': { 'operator': Not, 'child': { 'operator': Or, 'left': { 'operator': IsNull, 'child': { 'operator': Start, 'child': { 'operator': OperandRef, 'reference': 'period' } } }, 'right': { 'operator': Equal, 'left': { 'operator': Start, 'child': { 'operator': OperandRef, 'reference': 'period' } }, 'right': { 'operator': Unsupported, 'unsupportedOperator': 'MinValue', 'children': [] } } } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'period', 'children': [{ 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [153:1-158:3]
	define function "HasEnd"(period Interval<DateTime> ):
	  not ( 
	    end of period is null
	      or 
	      end of period = maximum DateTime
	  )
*/
{% macro MATGlobalCommonFunctionsHasEnd(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HasEnd', 'child': { 'operator': Not, 'child': { 'operator': Or, 'left': { 'operator': IsNull, 'child': { 'operator': End, 'child': { 'operator': OperandRef, 'reference': 'period' } } }, 'right': { 'operator': Equal, 'left': { 'operator': End, 'child': { 'operator': OperandRef, 'reference': 'period' } }, 'right': { 'operator': Unsupported, 'unsupportedOperator': 'MaxValue', 'children': [] } } } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'period', 'children': [{ 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [161:1-164:24]
	define function "Latest"(period Interval<DateTime> ):
	  if ( HasEnd(period)) then 
	  end of period 
	    else start of period
*/
{% macro MATGlobalCommonFunctionsLatest(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'Latest', 'child': { 'operator': If, 'condition': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHasEnd, 'children': [{ 'operator': OperandRef, 'reference': 'period' }] }, 'then': { 'operator': End, 'child': { 'operator': OperandRef, 'reference': 'period' } }, 'else': { 'operator': Start, 'child': { 'operator': OperandRef, 'reference': 'period' } } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'period', 'children': [{ 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [167:1-170:15]
	define function "Earliest"(period Interval<DateTime> ):
	  if ( HasStart(period)) then start of period 
	    else 
	  end of period
*/
{% macro MATGlobalCommonFunctionsEarliest(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'Earliest', 'child': { 'operator': If, 'condition': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHasStart, 'children': [{ 'operator': OperandRef, 'reference': 'period' }] }, 'then': { 'operator': Start, 'child': { 'operator': OperandRef, 'reference': 'period' } }, 'else': { 'operator': End, 'child': { 'operator': OperandRef, 'reference': 'period' } } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'period', 'children': [{ 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [173:1-174:48]
	define function "LatestOf"(pointInTime DateTime, period Interval<DateTime> ):
	  Latest(NormalizeInterval(pointInTime, period))
*/
{% macro MATGlobalCommonFunctionsLatestOf(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'LatestOf', 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsLatest, 'children': [{ 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsNormalizeInterval, 'children': [{ 'operator': OperandRef, 'reference': 'pointInTime' }, { 'operator': OperandRef, 'reference': 'period' }] }] }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'pointInTime', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, { 'operator': OperandDef, 'name': 'period', 'children': [{ 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }] }) }}{% endmacro %}
/*
	// MATGlobalCommonFunctions lines [177:1-178:50]
	define function "EarliestOf"(pointInTime DateTime, period Interval<DateTime> ):
	  Earliest(NormalizeInterval(pointInTime, period))
*/
{% macro MATGlobalCommonFunctionsEarliestOf(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'EarliestOf', 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsEarliest, 'children': [{ 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsNormalizeInterval, 'children': [{ 'operator': OperandRef, 'reference': 'pointInTime' }, { 'operator': OperandRef, 'reference': 'period' }] }] }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'pointInTime', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, { 'operator': OperandDef, 'name': 'period', 'children': [{ 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }] }) }}{% endmacro %}
