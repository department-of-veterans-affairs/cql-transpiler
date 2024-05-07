{%- import 'jinja_transpilation_libraries/sparksql/_operators_sparksql.sql' as _operators %}

{% import 'MATGlobalCommonFunctions_7.0.000.sql' as Global %}
{% import 'TJCOverall_7.1.000.sql' as TJC %}
/*
	// DischargedonAntithromboticTherapy lines [8:1-8:94]
	valueset "Antithrombotic Therapy for Ischemic Stroke": 'urn:oid:2.16.840.1.113762.1.4.1110.62'
*/
{% set Antithrombotic_Therapy_for_Ischemic_Stroke = 'urn:oid:2.16.840.1.113762.1.4.1110.62' %}
/*
	// DischargedonAntithromboticTherapy lines [9:1-9:58]
	valueset "Ethnicity": 'urn:oid:2.16.840.1.114222.4.11.837'
*/
{% set Ethnicity = 'urn:oid:2.16.840.1.114222.4.11.837' %}
/*
	// DischargedonAntithromboticTherapy lines [10:1-10:98]
	valueset "Medical Reason For Not Providing Treatment": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.473'
*/
{% set Medical_Reason_For_Not_Providing_Treatment = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.473' %}
/*
	// DischargedonAntithromboticTherapy lines [11:1-11:68]
	valueset "ONC Administrative Sex": 'urn:oid:2.16.840.1.113762.1.4.1'
*/
{% set ONC_Administrative_Sex = 'urn:oid:2.16.840.1.113762.1.4.1' %}
/*
	// DischargedonAntithromboticTherapy lines [12:1-12:70]
	valueset "Patient Refusal": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.93'
*/
{% set Patient_Refusal = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.93' %}
/*
	// DischargedonAntithromboticTherapy lines [13:1-13:55]
	valueset "Payer": 'urn:oid:2.16.840.1.114222.4.11.3591'
*/
{% set Payer = 'urn:oid:2.16.840.1.114222.4.11.3591' %}
/*
	// DischargedonAntithromboticTherapy lines [14:1-14:112]
	valueset "Pharmacological Contraindications For Antithrombotic Therapy": 'urn:oid:2.16.840.1.113762.1.4.1110.52'
*/
{% set Pharmacological_Contraindications_For_Antithrombotic_Therapy = 'urn:oid:2.16.840.1.113762.1.4.1110.52' %}
/*
	// DischargedonAntithromboticTherapy lines [15:1-15:53]
	valueset "Race": 'urn:oid:2.16.840.1.114222.4.11.836'
*/
{% set Race = 'urn:oid:2.16.840.1.114222.4.11.836' %}
/*
	// DischargedonAntithromboticTherapy lines [17:1-17:15]
	context Patient
*/
{% macro DischargedonAntithromboticTherapyPatient(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Patient', 'child': { 'operator': _operators.SingletonFrom, 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': none, 'codeComparator': none, 'codeProperty': none, 'child': none, 'valueSet': none } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyPatient(none) }}
/*
	// DischargedonAntithromboticTherapy lines [19:1-20:51]
	define "SDE Ethnicity":
	  ["Patient Characteristic Ethnicity": "Ethnicity"]
*/
{% macro DischargedonAntithromboticTherapySDE_Ethnicity(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'SDE Ethnicity', 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Ethnicity', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': Ethnicity } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapySDE_Ethnicity(none) }}
/*
	// DischargedonAntithromboticTherapy lines [22:1-23:43]
	define "SDE Payer":
	  ["Patient Characteristic Payer": "Payer"]
*/
{% macro DischargedonAntithromboticTherapySDE_Payer(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'SDE Payer', 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Payer', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': Payer } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapySDE_Payer(none) }}
/*
	// DischargedonAntithromboticTherapy lines [25:1-26:41]
	define "SDE Race":
	  ["Patient Characteristic Race": "Race"]
*/
{% macro DischargedonAntithromboticTherapySDE_Race(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'SDE Race', 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Race', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': Race } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapySDE_Race(none) }}
/*
	// DischargedonAntithromboticTherapy lines [28:1-29:58]
	define "SDE Sex":
	  ["Patient Characteristic Sex": "ONC Administrative Sex"]
*/
{% macro DischargedonAntithromboticTherapySDE_Sex(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'SDE Sex', 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Sex', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': ONC_Administrative_Sex } } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapySDE_Sex(none) }}
/*
	// DischargedonAntithromboticTherapy lines [31:1-32:33]
	define "Denominator":
	  TJC."Ischemic Stroke Encounter"
*/
{% macro DischargedonAntithromboticTherapyDenominator(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Denominator', 'child': { 'operator': _operators.ExpressionRef, 'reference': TJC.TJCOverallIschemic_Stroke_Encounter } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyDenominator(none) }}
/*
	// DischargedonAntithromboticTherapy lines [34:1-35:50]
	define "Initial Population":
	  TJC."Encounter with Principal Diagnosis and Age"
*/
{% macro DischargedonAntithromboticTherapyInitial_Population(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Initial Population', 'child': { 'operator': _operators.ExpressionRef, 'reference': TJC.TJCOverallEncounter_with_Principal_Diagnosis_and_Age } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyInitial_Population(none) }}
/*
	// DischargedonAntithromboticTherapy lines [37:1-39:70]
	define "Denominator Exclusions":
	  TJC."Ischemic Stroke Encounters with Discharge Disposition"
	    union TJC."Encounter with Comfort Measures during Hospitalization"
*/
{% macro DischargedonAntithromboticTherapyDenominator_Exclusions(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Denominator Exclusions', 'child': { 'operator': _operators.Union, 'children': [{ 'operator': _operators.ExpressionRef, 'reference': TJC.TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition }, { 'operator': _operators.ExpressionRef, 'reference': TJC.TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization }] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyDenominator_Exclusions(none) }}
/*
	// DischargedonAntithromboticTherapy lines [41:1-44:92]
	define "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Pharmacological Contraindications For Antithrombotic Therapy"] Pharmacological
	      such that Pharmacological.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
{% macro DischargedonAntithromboticTherapyEncounter_with_Pharmacological_Contraindications_for_Antithrombotic_Therapy_at_Discharge(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge', 'child': { 'operator': _operators.Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': _operators.ExpressionRef, 'reference': TJC.TJCOverallIschemic_Stroke_Encounter } }, { 'operator': _operators.With, 'alias': 'Pharmacological', 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveMedicationDischarge', 'resultTypeLabel': 'Medication, Discharge', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': Pharmacological_Contraindications_For_Antithrombotic_Therapy } }, 'suchThat': { 'operator': _operators.InInterval, 'left': { 'operator': _operators.Property, 'scope': 'Pharmacological', 'path': 'authorDatetime', 'child': none }, 'right': { 'operator': _operators.Property, 'scope': 'IschemicStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyEncounter_with_Pharmacological_Contraindications_for_Antithrombotic_Therapy_at_Discharge(none) }}
/*
	// DischargedonAntithromboticTherapy lines [46:1-49:73]
	define "Reason for Not Giving Antithrombotic at Discharge":
	  ["Medication, Not Discharged": "Antithrombotic Therapy for Ischemic Stroke"] NoAntithromboticDischarge
	    where NoAntithromboticDischarge.negationRationale in "Medical Reason For Not Providing Treatment"
	      or NoAntithromboticDischarge.negationRationale in "Patient Refusal"
*/
{% macro DischargedonAntithromboticTherapyReason_for_Not_Giving_Antithrombotic_at_Discharge(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Reason for Not Giving Antithrombotic at Discharge', 'child': { 'operator': _operators.Query, 'where': { 'operator': _operators.Or, 'left': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Medical_Reason_For_Not_Providing_Treatment }, 'valueSetExpression': none }, 'right': { 'operator': _operators.InValueSet, 'child': none, 'valueSetReference': { 'operator': _operators.ValueSetRef, 'reference': Patient_Refusal }, 'valueSetExpression': none } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'NoAntithromboticDischarge', 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'NegativeMedicationDischarge', 'resultTypeLabel': 'Medication, Not Discharged', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': Antithrombotic_Therapy_for_Ischemic_Stroke } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyReason_for_Not_Giving_Antithrombotic_at_Discharge(none) }}
/*
	// DischargedonAntithromboticTherapy lines [51:1-54:100]
	define "Numerator":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Antithrombotic Therapy for Ischemic Stroke"] DischargeAntithrombotic
	      such that DischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
{% macro DischargedonAntithromboticTherapyNumerator(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Numerator', 'child': { 'operator': _operators.Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': _operators.ExpressionRef, 'reference': TJC.TJCOverallIschemic_Stroke_Encounter } }, { 'operator': _operators.With, 'alias': 'DischargeAntithrombotic', 'child': { 'operator': _operators.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveMedicationDischarge', 'resultTypeLabel': 'Medication, Discharge', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': _operators.ValueSetRef, 'reference': Antithrombotic_Therapy_for_Ischemic_Stroke } }, 'suchThat': { 'operator': _operators.InInterval, 'left': { 'operator': _operators.Property, 'scope': 'DischargeAntithrombotic', 'path': 'authorDatetime', 'child': none }, 'right': { 'operator': _operators.Property, 'scope': 'IschemicStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyNumerator(none) }}
/*
	// DischargedonAntithromboticTherapy lines [56:1-59:102]
	define "Encounter with Documented Reason for No Antithrombotic At Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with "Reason for Not Giving Antithrombotic at Discharge" NoDischargeAntithrombotic
	      such that NoDischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
{% macro DischargedonAntithromboticTherapyEncounter_with_Documented_Reason_for_No_Antithrombotic_At_Discharge(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Documented Reason for No Antithrombotic At Discharge', 'child': { 'operator': _operators.Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': _operators.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': _operators.ExpressionRef, 'reference': TJC.TJCOverallIschemic_Stroke_Encounter } }, { 'operator': _operators.With, 'alias': 'NoDischargeAntithrombotic', 'child': { 'operator': _operators.ExpressionRef, 'reference': DischargedonAntithromboticTherapyReason_for_Not_Giving_Antithrombotic_at_Discharge }, 'suchThat': { 'operator': _operators.InInterval, 'left': { 'operator': _operators.Property, 'scope': 'NoDischargeAntithrombotic', 'path': 'authorDatetime', 'child': none }, 'right': { 'operator': _operators.Property, 'scope': 'IschemicStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } }], 'letClauseList': [] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyEncounter_with_Documented_Reason_for_No_Antithrombotic_At_Discharge(none) }}
/*
	// DischargedonAntithromboticTherapy lines [61:1-63:100]
	define "Denominator Exceptions":
	  "Encounter with Documented Reason for No Antithrombotic At Discharge"
	    union "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge"
*/
{% macro DischargedonAntithromboticTherapyDenominator_Exceptions(state) %}{{ _operators.printOperator(state, { 'operator': _operators.ExpressionDef, 'context': 'Patient', 'name': 'Denominator Exceptions', 'child': { 'operator': _operators.Union, 'children': [{ 'operator': _operators.ExpressionRef, 'reference': DischargedonAntithromboticTherapyEncounter_with_Documented_Reason_for_No_Antithrombotic_At_Discharge }, { 'operator': _operators.ExpressionRef, 'reference': DischargedonAntithromboticTherapyEncounter_with_Pharmacological_Contraindications_for_Antithrombotic_Therapy_at_Discharge }] } }) }}{% endmacro %}
{{ DischargedonAntithromboticTherapyDenominator_Exceptions(none) }}
