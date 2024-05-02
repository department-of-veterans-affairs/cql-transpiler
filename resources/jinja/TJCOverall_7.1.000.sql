{%- import 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' as _operators %}
{%- import 'jinja_transpilation_libraries/_custom_functions_sparksql.sql' as _custom_functions %}
{%- do _custom_functions.setup() %}

{% import 'MATGlobalCommonFunctions_7.0.000.sql' as Global %}
/*
	// TJCOverall lines [7:1-7:65]
	valueset "Comfort Measures": 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45' 
*/
{% set Comfort_Measures = 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45' %}
/*
	// TJCOverall lines [8:1-8:87]
	valueset "Discharge To Acute Care Facility": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.87' 
*/
{% set Discharge_To_Acute_Care_Facility = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.87' %}
/*
	// TJCOverall lines [9:1-9:107]
	valueset "Discharged to Health Care Facility for Hospice Care": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.207' 
*/
{% set Discharged_to_Health_Care_Facility_for_Hospice_Care = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.207' %}
/*
	// TJCOverall lines [10:1-10:91]
	valueset "Discharged to Home for Hospice Care": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.209' 
*/
{% set Discharged_to_Home_for_Hospice_Care = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.209' %}
/*
	// TJCOverall lines [11:1-11:82]
	valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292' 
*/
{% set Emergency_Department_Visit = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292' %}
/*
	// TJCOverall lines [12:1-12:74]
	valueset "Hemorrhagic Stroke": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.212' 
*/
{% set Hemorrhagic_Stroke = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.212' %}
/*
	// TJCOverall lines [13:1-13:71]
	valueset "Ischemic Stroke": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.247' 
*/
{% set Ischemic_Stroke = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.247' %}
/*
	// TJCOverall lines [14:1-14:83]
	valueset "Left Against Medical Advice": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.308' 
*/
{% set Left_Against_Medical_Advice = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.308' %}
/*
	// TJCOverall lines [15:1-15:87]
	valueset "Nonelective Inpatient Encounter": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424' 
*/
{% set Nonelective_Inpatient_Encounter = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424' %}
/*
	// TJCOverall lines [16:1-16:73]
	valueset "Observation Services": 'urn:oid:2.16.840.1.113762.1.4.1111.143' 
*/
{% set Observation_Services = 'urn:oid:2.16.840.1.113762.1.4.1111.143' %}
/*
	// TJCOverall lines [17:1-17:71]
	valueset "Patient Expired": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.309' 
*/
{% set Patient_Expired = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.309' %}
/*
	// TJCOverall lines [21:1-21:15]
	context Patient
*/
{% macro Patient(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Patient', 'child': { 'operator': _operators.SingletonFrom, 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': none, 'codeComparator': none, 'codeProperty': none, 'child': none, 'valueSet': none } } }) }}{% endmacro %}
{{ Patient(none) }}
/*
	// TJCOverall lines [23:1-25:57]
	define "Intervention Comfort Measures":
	  ["Intervention, Order": "Comfort Measures"]
	    union ["Intervention, Performed": "Comfort Measures"]
*/
{% macro Intervention_Comfort_Measures(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Intervention Comfort Measures', 'child': { 'operator': _operators.Union, 'children': [{ 'operator': _operators.As, 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionOrder', 'resultTypeLabel': 'Intervention, Order', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': Comfort_Measures } }, 'typeSpecifier': { 'operator': _operators.ListTypeSpecifier, 'children': [{ 'operator': _operators.Unsupported, 'unsupportedOperator': '_operators.ChoiceTypeSpecifier', 'children': [{ 'operator': _operators.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionPerformed', 'children': [] }, { 'operator': _operators.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionOrder', 'children': [] }] }] } }, { 'operator': _operators.As, 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionPerformed', 'resultTypeLabel': 'Intervention, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': Comfort_Measures } }, 'typeSpecifier': { 'operator': _operators.ListTypeSpecifier, 'children': [{ 'operator': _operators.Unsupported, 'unsupportedOperator': '_operators.ChoiceTypeSpecifier', 'children': [{ 'operator': _operators.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionPerformed', 'children': [] }, { 'operator': _operators.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionOrder', 'children': [] }] }] } }] } }) }}{% endmacro %}
{{ Intervention_Comfort_Measures(none) }}
/*
	// TJCOverall lines [58:1-61:85]
	define "Non Elective Inpatient Encounter":
	  ["Encounter, Performed": "Nonelective Inpatient Encounter"] NonElectiveEncounter
	    where Global."LengthInDays" ( NonElectiveEncounter.relevantPeriod ) <= 120
	      and NonElectiveEncounter.relevantPeriod ends during day of "Measurement Period"
*/
{% macro Non_Elective_Inpatient_Encounter(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Non Elective Inpatient Encounter', 'child': { 'operator': _operators.Query, 'where': { 'operator': _operators.And, 'left': { 'operator': _operators.LessOrEqual, 'left': { 'operator': _operators.FunctionRef, 'reference': Global.LengthInDays, 'children': [{ 'operator': _operators.Property, 'scope': 'NonElectiveEncounter', 'path': 'relevantPeriod', 'child': none }] }, 'right': { 'operator': _operators.Literal, 'type': 'Integer', 'value': '120' } }, 'right': { 'operator': _operators.InInterval, 'left': { 'operator': _operators.End, 'child': { 'operator': _operators.Property, 'scope': 'NonElectiveEncounter', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': _operators.ParameterRef, 'name': 'Measurement_Period' } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'NonElectiveEncounter', 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': Nonelective_Inpatient_Encounter } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ Non_Elective_Inpatient_Encounter(none) }}
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
{% macro All_Stroke_Encounter(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'All Stroke Encounter', 'child': { 'operator': _operators.Query, 'where': { 'operator': _operators.Exists, 'child': { 'operator': _operators.Query, 'where': { 'operator': _operators.And, 'left': { 'operator': _operators.Equal, 'left': { 'operator': _operators.Property, 'scope': 'Diagnosis', 'path': 'rank', 'child': none }, 'right': { 'operator': _operators.Literal, 'type': 'Integer', 'value': '1' } }, 'right': { 'operator': _operators.Or, 'left': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Hemorrhagic_Stroke }, 'valueSetExpression': none }, 'right': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Ischemic_Stroke }, 'valueSetExpression': none } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'Diagnosis', 'child': { 'operator': _operators.Property, 'scope': 'NonElectiveEncounter', 'path': 'diagnoses', 'child': none } }], 'letClauseList': [] } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'NonElectiveEncounter', 'child': { 'operator': _operators.ExpressionRef, 'reference': Non_Elective_Inpatient_Encounter } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ All_Stroke_Encounter(none) }}
/*
	// TJCOverall lines [63:1-65:81]
	define "Encounter with Principal Diagnosis and Age":
	  "All Stroke Encounter" AllStrokeEncounter
	    where AgeInYearsAt(date from start of AllStrokeEncounter.relevantPeriod)>= 18
*/
{% macro Encounter_with_Principal_Diagnosis_and_Age(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Principal Diagnosis and Age', 'child': { 'operator': _operators.Query, 'where': { 'operator': _operators.GreaterOrEqual, 'left': { 'operator': _operators.CalculateAgeAt, 'left': { 'operator': _operators.ToDate, 'child': { 'operator': _operators.Property, 'scope': none, 'path': 'birthDatetime', 'child': { 'operator': _operators.ExpressionRef, 'reference': Patient } } }, 'right': { 'operator': _operators.DateFrom, 'child': { 'operator': _operators.Start, 'child': { 'operator': _operators.Property, 'scope': 'AllStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } } }, 'right': { 'operator': _operators.Literal, 'type': 'Integer', 'value': '18' } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'AllStrokeEncounter', 'child': { 'operator': _operators.ExpressionRef, 'reference': All_Stroke_Encounter } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ Encounter_with_Principal_Diagnosis_and_Age(none) }}
/*
	// TJCOverall lines [37:1-42:5]
	define "Ischemic Stroke Encounter":
	  "Encounter with Principal Diagnosis and Age" EncounterWithAge
	    where exists ( EncounterWithAge.diagnoses Diagnosis
	        where Diagnosis.code in "Ischemic Stroke"
	          and Diagnosis.rank = 1
	    )
*/
{% macro Ischemic_Stroke_Encounter(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Ischemic Stroke Encounter', 'child': { 'operator': _operators.Query, 'where': { 'operator': _operators.Exists, 'child': { 'operator': _operators.Query, 'where': { 'operator': _operators.And, 'left': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Ischemic_Stroke }, 'valueSetExpression': none }, 'right': { 'operator': _operators.Equal, 'left': { 'operator': _operators.Property, 'scope': 'Diagnosis', 'path': 'rank', 'child': none }, 'right': { 'operator': _operators.Literal, 'type': 'Integer', 'value': '1' } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'Diagnosis', 'child': { 'operator': _operators.Property, 'scope': 'EncounterWithAge', 'path': 'diagnoses', 'child': none } }], 'letClauseList': [] } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'EncounterWithAge', 'child': { 'operator': _operators.ExpressionRef, 'reference': Encounter_with_Principal_Diagnosis_and_Age } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ Ischemic_Stroke_Encounter(none) }}
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
{% macro Ischemic_Stroke_Encounters_with_Discharge_Disposition(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Ischemic Stroke Encounters with Discharge Disposition', 'child': { 'operator': _operators.Query, 'where': { 'operator': _operators.Or, 'left': { 'operator': _operators.Or, 'left': { 'operator': _operators.Or, 'left': { 'operator': _operators.Or, 'left': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Discharge_To_Acute_Care_Facility }, 'valueSetExpression': none }, 'right': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Left_Against_Medical_Advice }, 'valueSetExpression': none } }, 'right': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Patient_Expired }, 'valueSetExpression': none } }, 'right': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Discharged_to_Home_for_Hospice_Care }, 'valueSetExpression': none } }, 'right': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Discharged_to_Health_Care_Facility_for_Hospice_Care }, 'valueSetExpression': none } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': _operators.ExpressionRef, 'reference': Ischemic_Stroke_Encounter } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ Ischemic_Stroke_Encounters_with_Discharge_Disposition(none) }}
/*
	// TJCOverall lines [53:1-56:230]
	define "Encounter with Comfort Measures during Hospitalization":
	  "Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with "Intervention Comfort Measures" ComfortMeasure
	      such that Coalesce(start of Global."NormalizeInterval"(ComfortMeasure.relevantDatetime, ComfortMeasure.relevantPeriod), ComfortMeasure.authorDatetime)during Global."HospitalizationWithObservation" ( IschemicStrokeEncounter )
*/
{% macro Encounter_with_Comfort_Measures_during_Hospitalization(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Comfort Measures during Hospitalization', 'child': { 'operator': _operators.Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': _operators.ExpressionRef, 'reference': Ischemic_Stroke_Encounter } }, { 'operator': _operators.With, 'alias': 'ComfortMeasure', 'child': { 'operator': _operators.ExpressionRef, 'reference': Intervention_Comfort_Measures }, 'suchThat': { 'operator': _operators.InInterval, 'left': { 'operator': _operators.Coalesce, 'children': [{ 'operator': _operators.Start, 'child': { 'operator': _operators.FunctionRef, 'reference': Global.NormalizeInterval, 'children': [{ 'operator': _operators.Property, 'scope': 'ComfortMeasure', 'path': 'relevantDatetime', 'child': none }, { 'operator': _operators.Property, 'scope': 'ComfortMeasure', 'path': 'relevantPeriod', 'child': none }] } }, { 'operator': _operators.Property, 'scope': 'ComfortMeasure', 'path': 'authorDatetime', 'child': none }] }, 'right': { 'operator': _operators.FunctionRef, 'reference': Global.HospitalizationWithObservation, 'children': [{ 'operator': _operators.AliasRef, 'name': 'IschemicStrokeEncounter' }] } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ Encounter_with_Comfort_Measures_during_Hospitalization(none) }}
/*
	// TJCOverall lines [67:1-68:81]
	define function "HospitalizationWithObservationLengthofStay"(Encounter "Encounter, Performed" ):
	  Global."LengthInDays" ( Global."HospitalizationWithObservation" ( Encounter ) )
*/
{% macro HospitalizationWithObservationLengthofStay(state) %}{{ _operators.printOperator(state, { 'operator': _operators.FunctionDef, 'context': 'Patient', 'name': 'HospitalizationWithObservationLengthofStay', 'child': { 'operator': _operators.FunctionRef, 'reference': Global.LengthInDays, 'children': [{ 'operator': _operators.FunctionRef, 'reference': Global.HospitalizationWithObservation, 'children': [{ 'operator': _operators.OperandRef, 'reference': 'Encounter' }] }] }, 'typeSpecifier': { 'operator': _operators.Disabled, 'children': [] }, 'operators': [{ 'operator': _operators.OperandDef, 'name': 'Encounter', 'children': [{ 'operator': _operators.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// TJCOverall lines [70:1-71:100]
	define function "TruncateTime"(Value DateTime ):
	  DateTime(year from Value, month from Value, day from Value, 0, 0, 0, 0, timezoneoffset from Value)
*/
{% macro TruncateTime(state) %}{{ _operators.printOperator(state, { 'operator': _operators.FunctionDef, 'context': 'Patient', 'name': 'TruncateTime', 'child': { 'operator': _operators.DateTime, 'year': { 'operator': _operators.Unsupported, 'unsupportedOperator': '_operators.DateTimeComponentFrom', 'children': [{ 'operator': _operators.OperandRef, 'reference': 'Value' }] }, 'month': { 'operator': _operators.Unsupported, 'unsupportedOperator': '_operators.DateTimeComponentFrom', 'children': [{ 'operator': _operators.OperandRef, 'reference': 'Value' }] }, 'day': { 'operator': _operators.Unsupported, 'unsupportedOperator': '_operators.DateTimeComponentFrom', 'children': [{ 'operator': _operators.OperandRef, 'reference': 'Value' }] }, 'hour': { 'operator': _operators.Literal, 'type': 'Integer', 'value': '0' }, 'minute': { 'operator': _operators.Literal, 'type': 'Integer', 'value': '0' }, 'second': { 'operator': _operators.Literal, 'type': 'Integer', 'value': '0' }, 'millisecond': { 'operator': _operators.Literal, 'type': 'Integer', 'value': '0' }, 'timezoneOffset': { 'operator': _operators.Unsupported, 'unsupportedOperator': '_operators.TimezoneOffsetFrom', 'children': [{ 'operator': _operators.OperandRef, 'reference': 'Value' }] } }, 'typeSpecifier': { 'operator': _operators.Disabled, 'children': [] }, 'operators': [{ 'operator': _operators.OperandDef, 'name': 'Value', 'children': [{ 'operator': _operators.NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }) }}{% endmacro %}
/*
	// TJCOverall lines [73:1-74:75]
	define function "CalendarDayOfOrDayAfter"(StartValue DateTime ):
	  Interval["TruncateTime"(StartValue), "TruncateTime"(StartValue + 2 days))
*/
{% macro CalendarDayOfOrDayAfter(state) %}{{ _operators.printOperator(state, { 'operator': _operators.FunctionDef, 'context': 'Patient', 'name': 'CalendarDayOfOrDayAfter', 'child': { 'operator': _operators.Interval, 'high': { 'operator': _operators.FunctionRef, 'reference': TruncateTime, 'children': [{ 'operator': _operators.Add, 'left': { 'operator': _operators.OperandRef, 'reference': 'StartValue' }, 'right': { 'operator': _operators.Quantity, 'unit': 'days', 'value': '2' } }] }, 'highClosedExpression': none, 'low': { 'operator': _operators.FunctionRef, 'reference': TruncateTime, 'children': [{ 'operator': _operators.OperandRef, 'reference': 'StartValue' }] }, 'lowClosedExpression': none }, 'typeSpecifier': { 'operator': _operators.IntervalTypeSpecifier, 'children': [{ 'operator': _operators.NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, 'operators': [{ 'operator': _operators.OperandDef, 'name': 'StartValue', 'children': [{ 'operator': _operators.NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }) }}{% endmacro %}
