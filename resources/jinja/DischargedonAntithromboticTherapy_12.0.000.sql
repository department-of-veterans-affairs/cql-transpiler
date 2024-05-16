{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import printOperator %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import UsingDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import IncludeDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ValueSetDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ContextDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ExpressionDef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import SingletonFrom %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Retrieve %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ValueSetRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import ExpressionRef %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Union %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Query %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import AliasedQuerySource %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import With %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import InInterval %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Property %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import Or %}
{%- from 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' import InValueSet %}
{%- from 'TJCOverall_7.1.000.sql' import TJCOverallIschemic_Stroke_Encounter %}
{%- from 'TJCOverall_7.1.000.sql' import TJCOverallEncounter_with_Principal_Diagnosis_and_Age %}
{%- from 'TJCOverall_7.1.000.sql' import TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition %}
{%- from 'TJCOverall_7.1.000.sql' import TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization %}
/*
	// DischargedonAntithromboticTherapy lines [8:1-8:94]
	valueset "Antithrombotic Therapy for Ischemic Stroke": 'urn:oid:2.16.840.1.113762.1.4.1110.62'
*/
{% set DischargedonAntithromboticTherapyAntithrombotic_Therapy_for_Ischemic_Stroke = 'urn:oid:2.16.840.1.113762.1.4.1110.62' %}
/*
	// DischargedonAntithromboticTherapy lines [9:1-9:58]
	valueset "Ethnicity": 'urn:oid:2.16.840.1.114222.4.11.837'
*/
{% set DischargedonAntithromboticTherapyEthnicity = 'urn:oid:2.16.840.1.114222.4.11.837' %}
/*
	// DischargedonAntithromboticTherapy lines [10:1-10:98]
	valueset "Medical Reason For Not Providing Treatment": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.473'
*/
{% set DischargedonAntithromboticTherapyMedical_Reason_For_Not_Providing_Treatment = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.473' %}
/*
	// DischargedonAntithromboticTherapy lines [11:1-11:68]
	valueset "ONC Administrative Sex": 'urn:oid:2.16.840.1.113762.1.4.1'
*/
{% set DischargedonAntithromboticTherapyONC_Administrative_Sex = 'urn:oid:2.16.840.1.113762.1.4.1' %}
/*
	// DischargedonAntithromboticTherapy lines [12:1-12:70]
	valueset "Patient Refusal": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.93'
*/
{% set DischargedonAntithromboticTherapyPatient_Refusal = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.93' %}
/*
	// DischargedonAntithromboticTherapy lines [13:1-13:55]
	valueset "Payer": 'urn:oid:2.16.840.1.114222.4.11.3591'
*/
{% set DischargedonAntithromboticTherapyPayer = 'urn:oid:2.16.840.1.114222.4.11.3591' %}
/*
	// DischargedonAntithromboticTherapy lines [14:1-14:112]
	valueset "Pharmacological Contraindications For Antithrombotic Therapy": 'urn:oid:2.16.840.1.113762.1.4.1110.52'
*/
{% set DischargedonAntithromboticTherapyPharmacological_Contraindications_For_Antithrombotic_Therapy = 'urn:oid:2.16.840.1.113762.1.4.1110.52' %}
/*
	// DischargedonAntithromboticTherapy lines [15:1-15:53]
	valueset "Race": 'urn:oid:2.16.840.1.114222.4.11.836'
*/
{% set DischargedonAntithromboticTherapyRace = 'urn:oid:2.16.840.1.114222.4.11.836' %}
/*
	// DischargedonAntithromboticTherapy lines [17:1-17:15]
	context Patient
*/
{% macro DischargedonAntithromboticTherapyPatient(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Patient', 'child': { 'operator': SingletonFrom, 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': none, 'codeComparator': none, 'codeProperty': none, 'child': none, 'valueSet': none } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyPatient(none) }}
/*
	// DischargedonAntithromboticTherapy lines [19:1-20:51]
	define "SDE Ethnicity":
	  ["Patient Characteristic Ethnicity": "Ethnicity"]
*/
{% macro DischargedonAntithromboticTherapySDE_Ethnicity(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'SDE Ethnicity', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Ethnicity', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': DischargedonAntithromboticTherapyEthnicity } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapySDE_Ethnicity(none) }}
/*
	// DischargedonAntithromboticTherapy lines [22:1-23:43]
	define "SDE Payer":
	  ["Patient Characteristic Payer": "Payer"]
*/
{% macro DischargedonAntithromboticTherapySDE_Payer(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'SDE Payer', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Payer', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': DischargedonAntithromboticTherapyPayer } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapySDE_Payer(none) }}
/*
	// DischargedonAntithromboticTherapy lines [25:1-26:41]
	define "SDE Race":
	  ["Patient Characteristic Race": "Race"]
*/
{% macro DischargedonAntithromboticTherapySDE_Race(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'SDE Race', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Race', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': DischargedonAntithromboticTherapyRace } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapySDE_Race(none) }}
/*
	// DischargedonAntithromboticTherapy lines [28:1-29:58]
	define "SDE Sex":
	  ["Patient Characteristic Sex": "ONC Administrative Sex"]
*/
{% macro DischargedonAntithromboticTherapySDE_Sex(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'SDE Sex', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Sex', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': DischargedonAntithromboticTherapyONC_Administrative_Sex } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapySDE_Sex(none) }}
/*
	// DischargedonAntithromboticTherapy lines [31:1-32:33]
	define "Denominator":
	  TJC."Ischemic Stroke Encounter"
*/
{% macro DischargedonAntithromboticTherapyDenominator(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Denominator', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyDenominator(none) }}
/*
	// DischargedonAntithromboticTherapy lines [34:1-35:50]
	define "Initial Population":
	  TJC."Encounter with Principal Diagnosis and Age"
*/
{% macro DischargedonAntithromboticTherapyInitial_Population(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Initial Population', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallEncounter_with_Principal_Diagnosis_and_Age } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyInitial_Population(none) }}
/*
	// DischargedonAntithromboticTherapy lines [37:1-39:70]
	define "Denominator Exclusions":
	  TJC."Ischemic Stroke Encounters with Discharge Disposition"
	    union TJC."Encounter with Comfort Measures during Hospitalization"
*/
{% macro DischargedonAntithromboticTherapyDenominator_Exclusions(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Denominator Exclusions', 'child': { 'operator': Union, 'children': [{ 'operator': ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition }, { 'operator': ExpressionRef, 'reference': TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization }] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyDenominator_Exclusions(none) }}
/*
	// DischargedonAntithromboticTherapy lines [41:1-44:92]
	define "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Pharmacological Contraindications For Antithrombotic Therapy"] Pharmacological
	      such that Pharmacological.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
{% macro DischargedonAntithromboticTherapyEncounter_with_Pharmacological_Contraindications_for_Antithrombotic_Therapy_at_Discharge(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge', 'child': { 'operator': Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }, { 'operator': With, 'alias': 'Pharmacological', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveMedicationDischarge', 'resultTypeLabel': 'Medication, Discharge', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': DischargedonAntithromboticTherapyPharmacological_Contraindications_For_Antithrombotic_Therapy } }, 'suchThat': { 'operator': InInterval, 'left': { 'operator': Property, 'scope': 'Pharmacological', 'path': 'authorDatetime', 'child': none }, 'right': { 'operator': Property, 'scope': 'IschemicStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyEncounter_with_Pharmacological_Contraindications_for_Antithrombotic_Therapy_at_Discharge(none) }}
/*
	// DischargedonAntithromboticTherapy lines [46:1-49:73]
	define "Reason for Not Giving Antithrombotic at Discharge":
	  ["Medication, Not Discharged": "Antithrombotic Therapy for Ischemic Stroke"] NoAntithromboticDischarge
	    where NoAntithromboticDischarge.negationRationale in "Medical Reason For Not Providing Treatment"
	      or NoAntithromboticDischarge.negationRationale in "Patient Refusal"
*/
{% macro DischargedonAntithromboticTherapyReason_for_Not_Giving_Antithrombotic_at_Discharge(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Reason for Not Giving Antithrombotic at Discharge', 'child': { 'operator': Query, 'where': { 'operator': Or, 'left': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': DischargedonAntithromboticTherapyMedical_Reason_For_Not_Providing_Treatment }, 'valueSetExpression': none }, 'right': { 'operator': InValueSet, 'child': none, 'valueSetReference': { 'operator': ValueSetRef, 'reference': DischargedonAntithromboticTherapyPatient_Refusal }, 'valueSetExpression': none } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'NoAntithromboticDischarge', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'NegativeMedicationDischarge', 'resultTypeLabel': 'Medication, Not Discharged', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': DischargedonAntithromboticTherapyAntithrombotic_Therapy_for_Ischemic_Stroke } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyReason_for_Not_Giving_Antithrombotic_at_Discharge(none) }}
/*
	// DischargedonAntithromboticTherapy lines [51:1-54:100]
	define "Numerator":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Antithrombotic Therapy for Ischemic Stroke"] DischargeAntithrombotic
	      such that DischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
{% macro DischargedonAntithromboticTherapyNumerator(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Numerator', 'child': { 'operator': Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }, { 'operator': With, 'alias': 'DischargeAntithrombotic', 'child': { 'operator': Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveMedicationDischarge', 'resultTypeLabel': 'Medication, Discharge', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': ValueSetRef, 'reference': DischargedonAntithromboticTherapyAntithrombotic_Therapy_for_Ischemic_Stroke } }, 'suchThat': { 'operator': InInterval, 'left': { 'operator': Property, 'scope': 'DischargeAntithrombotic', 'path': 'authorDatetime', 'child': none }, 'right': { 'operator': Property, 'scope': 'IschemicStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyNumerator(none) }}
/*
	// DischargedonAntithromboticTherapy lines [56:1-59:102]
	define "Encounter with Documented Reason for No Antithrombotic At Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with "Reason for Not Giving Antithrombotic at Discharge" NoDischargeAntithrombotic
	      such that NoDischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
{% macro DischargedonAntithromboticTherapyEncounter_with_Documented_Reason_for_No_Antithrombotic_At_Discharge(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Documented Reason for No Antithrombotic At Discharge', 'child': { 'operator': Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }, { 'operator': With, 'alias': 'NoDischargeAntithrombotic', 'child': { 'operator': ExpressionRef, 'reference': DischargedonAntithromboticTherapyReason_for_Not_Giving_Antithrombotic_at_Discharge }, 'suchThat': { 'operator': InInterval, 'left': { 'operator': Property, 'scope': 'NoDischargeAntithrombotic', 'path': 'authorDatetime', 'child': none }, 'right': { 'operator': Property, 'scope': 'IschemicStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyEncounter_with_Documented_Reason_for_No_Antithrombotic_At_Discharge(none) }}
/*
	// DischargedonAntithromboticTherapy lines [61:1-63:100]
	define "Denominator Exceptions":
	  "Encounter with Documented Reason for No Antithrombotic At Discharge"
	    union "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge"
*/
{% macro DischargedonAntithromboticTherapyDenominator_Exceptions(state) %}{{ printOperator(state, { 'operator': ExpressionDef, 'context': 'Patient', 'name': 'Denominator Exceptions', 'child': { 'operator': Union, 'children': [{ 'operator': ExpressionRef, 'reference': DischargedonAntithromboticTherapyEncounter_with_Documented_Reason_for_No_Antithrombotic_At_Discharge }, { 'operator': ExpressionRef, 'reference': DischargedonAntithromboticTherapyEncounter_with_Pharmacological_Contraindications_for_Antithrombotic_Therapy_at_Discharge }] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyDenominator_Exceptions(none) }}
