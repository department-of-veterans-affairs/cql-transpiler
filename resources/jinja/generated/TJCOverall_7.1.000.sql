{%- from 'MATGlobalCommonFunctions_7.0.000.sql' import MATGlobalCommonFunctionsLengthInDays %}
{%- from 'MATGlobalCommonFunctions_7.0.000.sql' import MATGlobalCommonFunctionsNormalizeInterval %}
{%- from 'MATGlobalCommonFunctions_7.0.000.sql' import MATGlobalCommonFunctionsHospitalizationWithObservation %}
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
{% macro TJCOverallPatient(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Patient', 'child': { 'operator': environment.SingletonFrom, 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': none, 'codeComparator': none, 'codeProperty': none, 'child': none, 'valueSet': none } } }) }}{% endmacro %}
/*
	// TJCOverall lines [23:1-25:57]
	define "Intervention Comfort Measures":
	  ["Intervention, Order": "Comfort Measures"]
	    union ["Intervention, Performed": "Comfort Measures"]
*/
{% macro TJCOverallIntervention_Comfort_Measures(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Intervention Comfort Measures', 'child': { 'operator': environment.Union, 'children': [{ 'operator': environment.As, 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionOrder', 'resultTypeLabel': 'Intervention, Order', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': TJCOverallComfort_Measures } }, 'typeSpecifier': { 'operator': environment.ListTypeSpecifier, 'children': [{ 'operator': environment.Unsupported, 'unsupportedOperator': 'environment.ChoiceTypeSpecifier', 'children': [{ 'operator': environment.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionPerformed', 'children': [] }, { 'operator': environment.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionOrder', 'children': [] }] }] } }, { 'operator': environment.As, 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionPerformed', 'resultTypeLabel': 'Intervention, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': TJCOverallComfort_Measures } }, 'typeSpecifier': { 'operator': environment.ListTypeSpecifier, 'children': [{ 'operator': environment.Unsupported, 'unsupportedOperator': 'environment.ChoiceTypeSpecifier', 'children': [{ 'operator': environment.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionPerformed', 'children': [] }, { 'operator': environment.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveInterventionOrder', 'children': [] }] }] } }] } }) }}{% endmacro %}
/*
	// TJCOverall lines [58:1-61:85]
	define "Non Elective Inpatient Encounter":
	  ["Encounter, Performed": "Nonelective Inpatient Encounter"] NonElectiveEncounter
	    where Global."LengthInDays" ( NonElectiveEncounter.relevantPeriod ) <= 120
	      and NonElectiveEncounter.relevantPeriod ends during day of "Measurement Period"
*/
{% macro TJCOverallNon_Elective_Inpatient_Encounter(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Non Elective Inpatient Encounter', 'child': { 'operator': environment.Query, 'where': { 'operator': environment.And, 'left': { 'operator': environment.LessOrEqual, 'left': { 'operator': environment.FunctionRef, 'reference': MATGlobalCommonFunctionsLengthInDays, 'children': [{ 'operator': environment.Property, 'scope': 'NonElectiveEncounter', 'path': 'relevantPeriod', 'child': none }] }, 'right': { 'operator': environment.Literal, 'type': 'Integer', 'value': '120' } }, 'right': { 'operator': environment.InInterval, 'left': { 'operator': environment.End, 'child': { 'operator': environment.Property, 'scope': 'NonElectiveEncounter', 'path': 'relevantPeriod', 'child': none } }, 'right': { 'operator': environment.ParameterRef, 'name': 'Measurement_Period' } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'NonElectiveEncounter', 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': TJCOverallNonelective_Inpatient_Encounter } } }], 'letClauseList': [] } }) }}{% endmacro %}
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
{% macro TJCOverallAll_Stroke_Encounter(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'All Stroke Encounter', 'child': { 'operator': environment.Query, 'where': { 'operator': environment.Exists, 'child': { 'operator': environment.Query, 'where': { 'operator': environment.And, 'left': { 'operator': environment.Equal, 'left': { 'operator': environment.Property, 'scope': 'Diagnosis', 'path': 'rank', 'child': none }, 'right': { 'operator': environment.Literal, 'type': 'Integer', 'value': '1' } }, 'right': { 'operator': environment.Or, 'left': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': TJCOverallHemorrhagic_Stroke }, 'valueSetExpression': none }, 'right': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': TJCOverallIschemic_Stroke }, 'valueSetExpression': none } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'Diagnosis', 'child': { 'operator': environment.Property, 'scope': 'NonElectiveEncounter', 'path': 'diagnoses', 'child': none } }], 'letClauseList': [] } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'NonElectiveEncounter', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallNon_Elective_Inpatient_Encounter } }], 'letClauseList': [] } }) }}{% endmacro %}
/*
	// TJCOverall lines [63:1-65:81]
	define "Encounter with Principal Diagnosis and Age":
	  "All Stroke Encounter" AllStrokeEncounter
	    where AgeInYearsAt(date from start of AllStrokeEncounter.relevantPeriod)>= 18
*/
{% macro TJCOverallEncounter_with_Principal_Diagnosis_and_Age(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Principal Diagnosis and Age', 'child': { 'operator': environment.Query, 'where': { 'operator': environment.GreaterOrEqual, 'left': { 'operator': environment.CalculateAgeAt, 'left': { 'operator': environment.ToDate, 'child': { 'operator': environment.Property, 'scope': none, 'path': 'birthDatetime', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallPatient } } }, 'right': { 'operator': environment.DateFrom, 'child': { 'operator': environment.Start, 'child': { 'operator': environment.Property, 'scope': 'AllStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } } }, 'right': { 'operator': environment.Literal, 'type': 'Integer', 'value': '18' } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'AllStrokeEncounter', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallAll_Stroke_Encounter } }], 'letClauseList': [] } }) }}{% endmacro %}
/*
	// TJCOverall lines [37:1-42:5]
	define "Ischemic Stroke Encounter":
	  "Encounter with Principal Diagnosis and Age" EncounterWithAge
	    where exists ( EncounterWithAge.diagnoses Diagnosis
	        where Diagnosis.code in "Ischemic Stroke"
	          and Diagnosis.rank = 1
	    )
*/
{% macro TJCOverallIschemic_Stroke_Encounter(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Ischemic Stroke Encounter', 'child': { 'operator': environment.Query, 'where': { 'operator': environment.Exists, 'child': { 'operator': environment.Query, 'where': { 'operator': environment.And, 'left': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': TJCOverallIschemic_Stroke }, 'valueSetExpression': none }, 'right': { 'operator': environment.Equal, 'left': { 'operator': environment.Property, 'scope': 'Diagnosis', 'path': 'rank', 'child': none }, 'right': { 'operator': environment.Literal, 'type': 'Integer', 'value': '1' } } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'Diagnosis', 'child': { 'operator': environment.Property, 'scope': 'EncounterWithAge', 'path': 'diagnoses', 'child': none } }], 'letClauseList': [] } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'EncounterWithAge', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallEncounter_with_Principal_Diagnosis_and_Age } }], 'letClauseList': [] } }) }}{% endmacro %}
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
{% macro TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Ischemic Stroke Encounters with Discharge Disposition', 'child': { 'operator': environment.Query, 'where': { 'operator': environment.Or, 'left': { 'operator': environment.Or, 'left': { 'operator': environment.Or, 'left': { 'operator': environment.Or, 'left': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': TJCOverallDischarge_To_Acute_Care_Facility }, 'valueSetExpression': none }, 'right': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': TJCOverallLeft_Against_Medical_Advice }, 'valueSetExpression': none } }, 'right': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': TJCOverallPatient_Expired }, 'valueSetExpression': none } }, 'right': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': TJCOverallDischarged_to_Home_for_Hospice_Care }, 'valueSetExpression': none } }, 'right': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': TJCOverallDischarged_to_Health_Care_Facility_for_Hospice_Care }, 'valueSetExpression': none } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }], 'letClauseList': [] } }) }}{% endmacro %}
/*
	// TJCOverall lines [53:1-56:230]
	define "Encounter with Comfort Measures during Hospitalization":
	  "Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with "Intervention Comfort Measures" ComfortMeasure
	      such that Coalesce(start of Global."NormalizeInterval"(ComfortMeasure.relevantDatetime, ComfortMeasure.relevantPeriod), ComfortMeasure.authorDatetime)during Global."HospitalizationWithObservation" ( IschemicStrokeEncounter )
*/
{% macro TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Comfort Measures during Hospitalization', 'child': { 'operator': environment.Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }, { 'operator': environment.With, 'alias': 'ComfortMeasure', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallIntervention_Comfort_Measures }, 'suchThat': { 'operator': environment.InInterval, 'left': { 'operator': environment.Coalesce, 'children': [{ 'operator': environment.Start, 'child': { 'operator': environment.FunctionRef, 'reference': MATGlobalCommonFunctionsNormalizeInterval, 'children': [{ 'operator': environment.Property, 'scope': 'ComfortMeasure', 'path': 'relevantDatetime', 'child': none }, { 'operator': environment.Property, 'scope': 'ComfortMeasure', 'path': 'relevantPeriod', 'child': none }] } }, { 'operator': environment.Property, 'scope': 'ComfortMeasure', 'path': 'authorDatetime', 'child': none }] }, 'right': { 'operator': environment.FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalizationWithObservation, 'children': [{ 'operator': environment.AliasRef, 'name': 'IschemicStrokeEncounter' }] } } }], 'letClauseList': [] } }) }}{% endmacro %}
/*
	// TJCOverall lines [67:1-68:81]
	define function "HospitalizationWithObservationLengthofStay"(Encounter "Encounter, Performed" ):
	  Global."LengthInDays" ( Global."HospitalizationWithObservation" ( Encounter ) )
*/
{% macro TJCOverallHospitalizationWithObservationLengthofStay(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.FunctionDef, 'context': 'Patient', 'name': 'HospitalizationWithObservationLengthofStay', 'child': { 'operator': environment.FunctionRef, 'reference': MATGlobalCommonFunctionsLengthInDays, 'children': [{ 'operator': environment.FunctionRef, 'reference': MATGlobalCommonFunctionsHospitalizationWithObservation, 'children': [{ 'operator': environment.OperandRef, 'reference': 'Encounter' }] }] }, 'typeSpecifier': { 'operator': environment.Disabled, 'children': [] }, 'operators': [{ 'operator': environment.OperandDef, 'name': 'Encounter', 'children': [{ 'operator': environment.NamedTypeSpecifier, 'name': '{urn:healthit-gov:qdm:v5_6}PositiveEncounterPerformed', 'children': [] }] }] }) }}{% endmacro %}
/*
	// TJCOverall lines [70:1-71:100]
	define function "TruncateTime"(Value DateTime ):
	  DateTime(year from Value, month from Value, day from Value, 0, 0, 0, 0, timezoneoffset from Value)
*/
{% macro TJCOverallTruncateTime(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.FunctionDef, 'context': 'Patient', 'name': 'TruncateTime', 'child': { 'operator': environment.DateTime, 'year': { 'operator': environment.Unsupported, 'unsupportedOperator': 'environment.DateTimeComponentFrom', 'children': [{ 'operator': environment.OperandRef, 'reference': 'Value' }] }, 'month': { 'operator': environment.Unsupported, 'unsupportedOperator': 'environment.DateTimeComponentFrom', 'children': [{ 'operator': environment.OperandRef, 'reference': 'Value' }] }, 'day': { 'operator': environment.Unsupported, 'unsupportedOperator': 'environment.DateTimeComponentFrom', 'children': [{ 'operator': environment.OperandRef, 'reference': 'Value' }] }, 'hour': { 'operator': environment.Literal, 'type': 'Integer', 'value': '0' }, 'minute': { 'operator': environment.Literal, 'type': 'Integer', 'value': '0' }, 'second': { 'operator': environment.Literal, 'type': 'Integer', 'value': '0' }, 'millisecond': { 'operator': environment.Literal, 'type': 'Integer', 'value': '0' }, 'timezoneOffset': { 'operator': environment.Unsupported, 'unsupportedOperator': 'environment.TimezoneOffsetFrom', 'children': [{ 'operator': environment.OperandRef, 'reference': 'Value' }] } }, 'typeSpecifier': { 'operator': environment.Disabled, 'children': [] }, 'operators': [{ 'operator': environment.OperandDef, 'name': 'Value', 'children': [{ 'operator': environment.NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }) }}{% endmacro %}
/*
	// TJCOverall lines [73:1-74:75]
	define function "CalendarDayOfOrDayAfter"(StartValue DateTime ):
	  Interval["TruncateTime"(StartValue), "TruncateTime"(StartValue + 2 days))
*/
{% macro TJCOverallCalendarDayOfOrDayAfter(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.FunctionDef, 'context': 'Patient', 'name': 'CalendarDayOfOrDayAfter', 'child': { 'operator': environment.Interval, 'high': { 'operator': environment.FunctionRef, 'reference': TJCOverallTruncateTime, 'children': [{ 'operator': environment.Add, 'left': { 'operator': environment.OperandRef, 'reference': 'StartValue' }, 'right': { 'operator': environment.Quantity, 'unit': 'days', 'value': '2' } }] }, 'highClosedExpression': none, 'low': { 'operator': environment.FunctionRef, 'reference': TJCOverallTruncateTime, 'children': [{ 'operator': environment.OperandRef, 'reference': 'StartValue' }] }, 'lowClosedExpression': none }, 'typeSpecifier': { 'operator': environment.IntervalTypeSpecifier, 'children': [{ 'operator': environment.NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }, 'operators': [{ 'operator': environment.OperandDef, 'name': 'StartValue', 'children': [{ 'operator': environment.NamedTypeSpecifier, 'name': '{urn:hl7-org:elm-types:r1}DateTime', 'children': [] }] }] }) }}{% endmacro %}
