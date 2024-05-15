{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import printOperator %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import UsingDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import IncludeDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ValueSetDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ParameterDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import IntervalTypeSpecifier %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import NamedTypeSpecifier %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ContextDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ExpressionDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import SingletonFrom %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Retrieve %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Union %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import As %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ValueSetRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ListTypeSpecifier %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Unsupported %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Query %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import AliasedQuerySource %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import And %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import LessOrEqual %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import FunctionRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Property %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Literal %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import InInterval %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import End %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ParameterRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ExpressionRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Exists %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Equal %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Or %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import InValueSet %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import GreaterOrEqual %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import CalculateAgeAt %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ToDate %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import DateFrom %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Start %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import With %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Coalesce %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import AliasRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import FunctionDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import OperandRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import OperandDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import DateTime %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Interval %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Add %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Quantity %}
{%- from 'jinja_transpilation_libraries/sparksql/MATGlobalCommonFunctions.sql' import MATGlobalCommonFunctionsLengthInDays %}
{%- from 'jinja_transpilation_libraries/sparksql/MATGlobalCommonFunctions.sql' import MATGlobalCommonFunctionsNormalizeInterval %}
{%- from 'jinja_transpilation_libraries/sparksql/MATGlobalCommonFunctions.sql' import MATGlobalCommonFunctionsHospitalizationWithObservation %}
/*
	// TJCOverall lines [7:1-7:65]
	valueset "Comfort Measures": 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45' 
*/
{% set TJCOverallComfort_Measures = 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45' %}
/*
	// TJCOverall lines [8:1-8:87]
	valueset "Discharge To Acute Care Facility": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.87' 
*/
{% set TJCOverallDischarge_To_Acute_Care_Facility = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.87' %}
/*
	// TJCOverall lines [9:1-9:107]
	valueset "Discharged to Health Care Facility for Hospice Care": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.207' 
*/
{% set TJCOverallDischarged_to_Health_Care_Facility_for_Hospice_Care = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.207' %}
/*
	// TJCOverall lines [10:1-10:91]
	valueset "Discharged to Home for Hospice Care": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.209' 
*/
{% set TJCOverallDischarged_to_Home_for_Hospice_Care = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.209' %}
/*
	// TJCOverall lines [11:1-11:82]
	valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292' 
*/
{% set TJCOverallEmergency_Department_Visit = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292' %}
/*
	// TJCOverall lines [12:1-12:74]
	valueset "Hemorrhagic Stroke": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.212' 
*/
{% set TJCOverallHemorrhagic_Stroke = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.212' %}
/*
	// TJCOverall lines [13:1-13:71]
	valueset "Ischemic Stroke": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.247' 
*/
{% set TJCOverallIschemic_Stroke = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.247' %}
/*
	// TJCOverall lines [14:1-14:83]
	valueset "Left Against Medical Advice": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.308' 
*/
{% set TJCOverallLeft_Against_Medical_Advice = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.308' %}
/*
	// TJCOverall lines [15:1-15:87]
	valueset "Nonelective Inpatient Encounter": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424' 
*/
{% set TJCOverallNonelective_Inpatient_Encounter = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424' %}
/*
	// TJCOverall lines [16:1-16:73]
	valueset "Observation Services": 'urn:oid:2.16.840.1.113762.1.4.1111.143' 
*/
{% set TJCOverallObservation_Services = 'urn:oid:2.16.840.1.113762.1.4.1111.143' %}
/*
	// TJCOverall lines [17:1-17:71]
	valueset "Patient Expired": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.309' 
*/
{% set TJCOverallPatient_Expired = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.309' %}
/*
	// TJCOverall lines [21:1-21:15]
	context Patient
*/
{% macro TJCOverallPatient(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Patient', 'child': { 'operator': SingletonFrom, 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': none, 'codeComparator': none, 'codeProperty': none, 'child': none, 'valueSet': none } } }) }}{% endmacro %}
{{ TJCOverallPatient(none) }}
/*
	// TJCOverall lines [23:1-25:57]
	define "Intervention Comfort Measures":
	  ["Intervention, Order": "Comfort Measures"]
	    union ["Intervention, Performed": "Comfort Measures"]
*/
{% macro TJCOverallIntervention_Comfort_Measures(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Intervention Comfort Measures', 'child': { 'operator': Union, 'children': [{ 'operator': As, 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionOrder', 'resultTypeLabel': 'Intervention, Order', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': TJCOverallComfort_Measures } }, 'typeSpecifier': { 'operator': ListTypeSpecifier, 'children': [{ 'operator': Unsupported, 'unsupportedOperator': 'ChoiceTypeSpecifier', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionPerformed', 'children': [] }, { 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionOrder', 'children': [] }] }] } }, { 'operator': As, 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionPerformed', 'resultTypeLabel': 'Intervention, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': TJCOverallComfort_Measures } }, 'typeSpecifier': { 'operator': ListTypeSpecifier, 'children': [{ 'operator': Unsupported, 'unsupportedOperator': 'ChoiceTypeSpecifier', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionPerformed', 'children': [] }, { 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionOrder', 'children': [] }] }] } }] } }) }}{% endmacro %}
{{ TJCOverallIntervention_Comfort_Measures(none) }}
/*
	// TJCOverall lines [58:1-61:85]
	define "Non Elective Inpatient Encounter":
	  ["Encounter, Performed": "Nonelective Inpatient Encounter"] NonElectiveEncounter
	    where Global."LengthInDays" ( NonElectiveEncounter.relevantPeriod ) <= 120
	      and NonElectiveEncounter.relevantPeriod ends during day of "Measurement Period"
*/
{% macro TJCOverallNon_Elective_Inpatient_Encounter(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Non Elective Inpatient Encounter', 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': LessOrEqual, 'left': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsLengthInDays, 'children': [{ 'operator': Property, 'scope': 'NonElectiveEncounter', 'path': 'relevantPeriod', 'child': none }] }, 'right': { 'operator': Literal, 'type': 'Integer', 'value': '120' } }, 'right': { 'operator': InInterval, 'left': { 'operator': End, 'child': { 'operator': Property, 'scope': 'NonElectiveEncounter', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': ParameterRef, 'name': 'Measurement_Period' } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'NonElectiveEncounter', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': TJCOverallNonelective_Inpatient_Encounter } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ TJCOverallNon_Elective_Inpatient_Encounter(none) }}
/*
	// TJCOverall lines [44:1-51:5]
	define "All Stroke Encounter":
	  "Non Elective Inpatient Encounter" NonElectiveEncounter
	    where exists ( NonElectiveEncounter.diagnoses Diagnosis
	        where Diagnosis.rank = 1
	          and ( Diagnosis.code in "Hemorrhagic Stroke"
	              or Diagnosis.code in "Ischemic Stroke"
	          )
	    )
*/
{% macro TJCOverallAll_Stroke_Encounter(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'All Stroke Encounter', 'child': { 'operator': Query, 'where': { 'operator': Exists, 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': Equal, 'left': { 'operator': Property, 'scope': 'Diagnosis', 'path': 'rank', 'child': none }, 'right': { 'operator': Literal, 'type': 'Integer', 'value': '1' } }, 'right': { 'operator': Or, 'left': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': TJCOverallHemorrhagic_Stroke }, 'valueSetExpression': none }, 'right': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': TJCOverallIschemic_Stroke }, 'valueSetExpression': none } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'Diagnosis', 'child': { 'operator': Property, 'scope': 'NonElectiveEncounter', 'path': 'diagnoses', 'child': none } }], 'letClauseList': [] } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'NonElectiveEncounter', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallNon_Elective_Inpatient_Encounter } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ TJCOverallAll_Stroke_Encounter(none) }}
/*
	// TJCOverall lines [63:1-65:81]
	define "Encounter with Principal Diagnosis and Age":
	  "All Stroke Encounter" AllStrokeEncounter
	    where AgeInYearsAt(date from start of AllStrokeEncounter.relevantPeriod)>= 18
*/
{% macro TJCOverallEncounter_with_Principal_Diagnosis_and_Age(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Principal Diagnosis and Age', 'child': { 'operator': Query, 'where': { 'operator': GreaterOrEqual, 'left': { 'operator': CalculateAgeAt, 'left': { 'operator': ToDate, 'child': { 'operator': Property, 'scope': none, 'path': 'birthDatetime', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallPatient } } }, 'right': { 'operator': DateFrom, 'child': { 'operator': Start, 'child': { 'operator': Property, 'scope': 'AllStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } } }, 'right': { 'operator': Literal, 'type': 'Integer', 'value': '18' } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'AllStrokeEncounter', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallAll_Stroke_Encounter } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ TJCOverallEncounter_with_Principal_Diagnosis_and_Age(none) }}
/*
	// TJCOverall lines [37:1-42:5]
	define "Ischemic Stroke Encounter":
	  "Encounter with Principal Diagnosis and Age" EncounterWithAge
	    where exists ( EncounterWithAge.diagnoses Diagnosis
	        where Diagnosis.code in "Ischemic Stroke"
	          and Diagnosis.rank = 1
	    )
*/
{% macro TJCOverallIschemic_Stroke_Encounter(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Ischemic Stroke Encounter', 'child': { 'operator': Query, 'where': { 'operator': Exists, 'child': { 'operator': Query, 'where': { 'operator': And, 'left': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': TJCOverallIschemic_Stroke }, 'valueSetExpression': none }, 'right': { 'operator': Equal, 'left': { 'operator': Property, 'scope': 'Diagnosis', 'path': 'rank', 'child': none }, 'right': { 'operator': Literal, 'type': 'Integer', 'value': '1' } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'Diagnosis', 'child': { 'operator': Property, 'scope': 'EncounterWithAge', 'path': 'diagnoses', 'child': none } }], 'letClauseList': [] } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'EncounterWithAge', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallEncounter_with_Principal_Diagnosis_and_Age } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ TJCOverallIschemic_Stroke_Encounter(none) }}
/*
	// TJCOverall lines [27:1-35:3]
	define "Ischemic Stroke Encounters with Discharge Disposition":
	  ( ( "Ischemic Stroke Encounter" IschemicStrokeEncounter
	        where IschemicStrokeEncounter.dischargeDisposition in "Discharge To Acute Care Facility"
	          or IschemicStrokeEncounter.dischargeDisposition in "Left Against Medical Advice"
	          or IschemicStrokeEncounter.dischargeDisposition in "Patient Expired"
	          or IschemicStrokeEncounter.dischargeDisposition in "Discharged to Home for Hospice Care"
	          or IschemicStrokeEncounter.dischargeDisposition in "Discharged to Health Care Facility for Hospice Care"
	    )
	  )
*/
{% macro TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Ischemic Stroke Encounters with Discharge Disposition', 'child': { 'operator': Query, 'where': { 'operator': Or, 'left': { 'operator': Or, 'left': { 'operator': Or, 'left': { 'operator': Or, 'left': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': TJCOverallDischarge_To_Acute_Care_Facility }, 'valueSetExpression': none }, 'right': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': TJCOverallLeft_Against_Medical_Advice }, 'valueSetExpression': none } }, 'right': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': TJCOverallPatient_Expired }, 'valueSetExpression': none } }, 'right': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': TJCOverallDischarged_to_Home_for_Hospice_Care }, 'valueSetExpression': none } }, 'right': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': TJCOverallDischarged_to_Health_Care_Facility_for_Hospice_Care }, 'valueSetExpression': none } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition(none) }}
/*
	// TJCOverall lines [53:1-56:230]
	define "Encounter with Comfort Measures during Hospitalization":
	  "Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with "Intervention Comfort Measures" ComfortMeasure
	      such that Coalesce(start of Global."NormalizeInterval"(ComfortMeasure.relevantDatetime, ComfortMeasure.relevantPeriod), ComfortMeasure.authorDatetime)during Global."HospitalizationWithObservation" ( IschemicStrokeEncounter )
*/
{% macro TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Comfort Measures during Hospitalization', 'child': { 'operator': Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }, { 'operator': With, 'alias': 'ComfortMeasure', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallIntervention_Comfort_Measures }, 'suchThat': { 'operator': InInterval, 'left': { 'operator': Coalesce, 'children': [{ 'operator': Start, 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsNormalizeInterval, 'children': [{ 'operator': Property, 'scope': 'ComfortMeasure', 'path': 'relevantDatetime', 'child': none }, { 'operator': Property, 'scope': 'ComfortMeasure', 'path': 'relevantPeriod', 'child': none }] } }, { 'operator': Property, 'scope': 'ComfortMeasure', 'path': 'authorDatetime', 'child': none }] }, 'right': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalizationWithObservation, 'children': [{ 'operator': AliasRef, 'name': 'IschemicStrokeEncounter' }] } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization(none) }}
/*
	// TJCOverall lines [67:1-68:81]
	define function "HospitalizationWithObservationLengthofStay"(Encounter "Encounter, Performed" ):
	  Global."LengthInDays" ( Global."HospitalizationWithObservation" ( Encounter ) )
*/
{% macro TJCOverallHospitalizationWithObservationLengthofStay(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'HospitalizationWithObservationLengthofStay', 'child': { 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsLengthInDays, 'children': [{ 'operator': FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalizationWithObservation, 'children': [{ 'operator': OperandRef, 'reference': 'Encounter' }] }] }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Encounter', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// TJCOverall lines [70:1-71:100]
	define function "TruncateTime"(Value DateTime ):
	  DateTime(year from Value, month from Value, day from Value, 0, 0, 0, 0, timezoneoffset from Value)
*/
{% macro TJCOverallTruncateTime(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'TruncateTime', 'child': { 'operator': DateTime, 'year': { 'operator': Unsupported, 'unsupportedOperator': 'DateTimeComponentFrom', 'children': [{ 'operator': OperandRef, 'reference': 'Value' }] }, 'month': { 'operator': Unsupported, 'unsupportedOperator': 'DateTimeComponentFrom', 'children': [{ 'operator': OperandRef, 'reference': 'Value' }] }, 'day': { 'operator': Unsupported, 'unsupportedOperator': 'DateTimeComponentFrom', 'children': [{ 'operator': OperandRef, 'reference': 'Value' }] }, 'hour': { 'operator': Literal, 'type': 'Integer', 'value': '0' }, 'minute': { 'operator': Literal, 'type': 'Integer', 'value': '0' }, 'second': { 'operator': Literal, 'type': 'Integer', 'value': '0' }, 'millisecond': { 'operator': Literal, 'type': 'Integer', 'value': '0' }, 'timezoneOffset': { 'operator': Unsupported, 'unsupportedOperator': 'TimezoneOffsetFrom', 'children': [{ 'operator': OperandRef, 'reference': 'Value' }] } }, 'typeSpecifier': { 'operator': Disabled, 'children': [] }, 'operators': [{ 'operator': OperandDef, 'name': 'Value', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }) }}{% endmacro %}
/*
	// TJCOverall lines [73:1-74:75]
	define function "CalendarDayOfOrDayAfter"(StartValue DateTime ):
	  Interval["TruncateTime"(StartValue), "TruncateTime"(StartValue + 2 days))
*/
{% macro TJCOverallCalendarDayOfOrDayAfter(state) %}{{ printOperator(state, { 'operator': FunctionDef, 'context': 'Patient', 'name': 'CalendarDayOfOrDayAfter', 'child': { 'operator': Interval, 'high': { 'operator': FunctionRef, 'reference': TJCOverallTruncateTime, 'children': [{ 'operator': Add, 'left': { 'operator': OperandRef, 'reference': 'StartValue' }, 'right': { 'operator': Quantity, 'unit': 'days', 'value': '2' } }] }, 'highClosedExpression': none, 'low': { 'operator': FunctionRef, 'reference': TJCOverallTruncateTime, 'children': [{ 'operator': OperandRef, 'reference': 'StartValue' }] }, 'lowClosedExpression': none }, 'typeSpecifier': { 'operator': IntervalTypeSpecifier, 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, 'operators': [{ 'operator': OperandDef, 'name': 'StartValue', 'children': [{ 'operator': NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }) }}{% endmacro %}
