{%- from 'generated/TJCOverall_7.1.000.sql' import TJCOverallIschemic_Stroke_Encounter %}
{%- from 'generated/TJCOverall_7.1.000.sql' import TJCOverallEncounter_with_Principal_Diagnosis_and_Age %}
{%- from 'generated/TJCOverall_7.1.000.sql' import TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition %}
{%- from 'generated/TJCOverall_7.1.000.sql' import TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization %}
/*
	// DischargedonAntithromboticTherapy lines [8:1-8:94]
	valueset "Antithrombotic Therapy for Ischemic Stroke": 'urn:oid:2.16.840.1.113762.1.4.1110.62'
*/
{% macro DischargedonAntithromboticTherapyAntithrombotic_Therapy_for_Ischemic_Stroke() %}urn:oid:2.16.840.1.113762.1.4.1110.62{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [9:1-9:58]
	valueset "Ethnicity": 'urn:oid:2.16.840.1.114222.4.11.837'
*/
{% macro DischargedonAntithromboticTherapyEthnicity() %}urn:oid:2.16.840.1.114222.4.11.837{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [10:1-10:98]
	valueset "Medical Reason For Not Providing Treatment": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.473'
*/
{% macro DischargedonAntithromboticTherapyMedical_Reason_For_Not_Providing_Treatment() %}urn:oid:2.16.840.1.113883.3.117.1.7.1.473{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [11:1-11:68]
	valueset "ONC Administrative Sex": 'urn:oid:2.16.840.1.113762.1.4.1'
*/
{% macro DischargedonAntithromboticTherapyONC_Administrative_Sex() %}urn:oid:2.16.840.1.113762.1.4.1{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [12:1-12:70]
	valueset "Patient Refusal": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.93'
*/
{% macro DischargedonAntithromboticTherapyPatient_Refusal() %}urn:oid:2.16.840.1.113883.3.117.1.7.1.93{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [13:1-13:55]
	valueset "Payer": 'urn:oid:2.16.840.1.114222.4.11.3591'
*/
{% macro DischargedonAntithromboticTherapyPayer() %}urn:oid:2.16.840.1.114222.4.11.3591{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [14:1-14:112]
	valueset "Pharmacological Contraindications For Antithrombotic Therapy": 'urn:oid:2.16.840.1.113762.1.4.1110.52'
*/
{% macro DischargedonAntithromboticTherapyPharmacological_Contraindications_For_Antithrombotic_Therapy() %}urn:oid:2.16.840.1.113762.1.4.1110.52{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [15:1-15:53]
	valueset "Race": 'urn:oid:2.16.840.1.114222.4.11.836'
*/
{% macro DischargedonAntithromboticTherapyRace() %}urn:oid:2.16.840.1.114222.4.11.836{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [17:1-17:15]
	context Patient
*/
{% macro DischargedonAntithromboticTherapyPatient(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Patient', 'child': { 'operator': environment.SingletonFrom, 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': none, 'codeComparator': none, 'codeProperty': none, 'child': none, 'valueSet': none } } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [19:1-20:51]
	define "SDE Ethnicity":
	  ["Patient Characteristic Ethnicity": "Ethnicity"]
*/
{% macro DischargedonAntithromboticTherapySDE_Ethnicity(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'SDE Ethnicity', 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Ethnicity', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': DischargedonAntithromboticTherapyEthnicity() } } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [22:1-23:43]
	define "SDE Payer":
	  ["Patient Characteristic Payer": "Payer"]
*/
{% macro DischargedonAntithromboticTherapySDE_Payer(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'SDE Payer', 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Payer', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': DischargedonAntithromboticTherapyPayer() } } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [25:1-26:41]
	define "SDE Race":
	  ["Patient Characteristic Race": "Race"]
*/
{% macro DischargedonAntithromboticTherapySDE_Race(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'SDE Race', 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Race', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': DischargedonAntithromboticTherapyRace() } } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [28:1-29:58]
	define "SDE Sex":
	  ["Patient Characteristic Sex": "ONC Administrative Sex"]
*/
{% macro DischargedonAntithromboticTherapySDE_Sex(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'SDE Sex', 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': none, 'resultTypeLabel': 'Patient Characteristic Sex', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': DischargedonAntithromboticTherapyONC_Administrative_Sex() } } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [31:1-32:33]
	define "Denominator":
	  TJC."Ischemic Stroke Encounter"
*/
{% macro DischargedonAntithromboticTherapyDenominator(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Denominator', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [34:1-35:50]
	define "Initial Population":
	  TJC."Encounter with Principal Diagnosis and Age"
*/
{% macro DischargedonAntithromboticTherapyInitial_Population(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Initial Population', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallEncounter_with_Principal_Diagnosis_and_Age } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [37:1-39:70]
	define "Denominator Exclusions":
	  TJC."Ischemic Stroke Encounters with Discharge Disposition"
	    union TJC."Encounter with Comfort Measures during Hospitalization"
*/
{% macro DischargedonAntithromboticTherapyDenominator_Exclusions(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Denominator Exclusions', 'child': { 'operator': environment.Union, 'children': [{ 'operator': environment.ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition }, { 'operator': environment.ExpressionRef, 'reference': TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization }] } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [41:1-44:92]
	define "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Pharmacological Contraindications For Antithrombotic Therapy"] Pharmacological
	      such that Pharmacological.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
{% macro DischargedonAntithromboticTherapyEncounter_with_Pharmacological_Contraindications_for_Antithrombotic_Therapy_at_Discharge(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge', 'child': { 'operator': environment.Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }, { 'operator': environment.With, 'alias': 'Pharmacological', 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveMedicationDischarge', 'resultTypeLabel': 'Medication, Discharge', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': DischargedonAntithromboticTherapyPharmacological_Contraindications_For_Antithrombotic_Therapy() } }, 'suchThat': { 'operator': environment.InInterval, 'left': { 'operator': environment.Property, 'scope': 'Pharmacological', 'path': 'authorDatetime', 'child': none }, 'right': { 'operator': environment.Property, 'scope': 'IschemicStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } }], 'letClauseList': [] } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [46:1-49:73]
	define "Reason for Not Giving Antithrombotic at Discharge":
	  ["Medication, Not Discharged": "Antithrombotic Therapy for Ischemic Stroke"] NoAntithromboticDischarge
	    where NoAntithromboticDischarge.negationRationale in "Medical Reason For Not Providing Treatment"
	      or NoAntithromboticDischarge.negationRationale in "Patient Refusal"
*/
{% macro DischargedonAntithromboticTherapyReason_for_Not_Giving_Antithrombotic_at_Discharge(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Reason for Not Giving Antithrombotic at Discharge', 'child': { 'operator': environment.Query, 'where': { 'operator': environment.Or, 'left': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': DischargedonAntithromboticTherapyMedical_Reason_For_Not_Providing_Treatment() }, 'valueSetExpression': none }, 'right': { 'operator': environment.InValueSet, 'child': none, 'valueSetReference': { 'operator': environment.ValueSetRef, 'reference': DischargedonAntithromboticTherapyPatient_Refusal() }, 'valueSetExpression': none } }, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'NoAntithromboticDischarge', 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'NegativeMedicationDischarge', 'resultTypeLabel': 'Medication, Not Discharged', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': DischargedonAntithromboticTherapyAntithrombotic_Therapy_for_Ischemic_Stroke() } } }], 'letClauseList': [] } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [51:1-54:100]
	define "Numerator":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Antithrombotic Therapy for Ischemic Stroke"] DischargeAntithrombotic
	      such that DischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
{% macro DischargedonAntithromboticTherapyNumerator(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Numerator', 'child': { 'operator': environment.Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }, { 'operator': environment.With, 'alias': 'DischargeAntithrombotic', 'child': { 'operator': environment.Retrieve, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveMedicationDischarge', 'resultTypeLabel': 'Medication, Discharge', 'codeComparator': 'in', 'codeProperty': 'code', 'child': none, 'valueSet': { 'operator': environment.ValueSetRef, 'reference': DischargedonAntithromboticTherapyAntithrombotic_Therapy_for_Ischemic_Stroke() } }, 'suchThat': { 'operator': environment.InInterval, 'left': { 'operator': environment.Property, 'scope': 'DischargeAntithrombotic', 'path': 'authorDatetime', 'child': none }, 'right': { 'operator': environment.Property, 'scope': 'IschemicStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } }], 'letClauseList': [] } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [56:1-59:102]
	define "Encounter with Documented Reason for No Antithrombotic At Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with "Reason for Not Giving Antithrombotic at Discharge" NoDischargeAntithrombotic
	      such that NoDischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
{% macro DischargedonAntithromboticTherapyEncounter_with_Documented_Reason_for_No_Antithrombotic_At_Discharge(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Encounter with Documented Reason for No Antithrombotic At Discharge', 'child': { 'operator': environment.Query, 'where': none, 'returnClause': none, 'sortClause': none, 'children': [{ 'operator': environment.AliasedQuerySource, 'alias': 'IschemicStrokeEncounter', 'child': { 'operator': environment.ExpressionRef, 'reference': TJCOverallIschemic_Stroke_Encounter } }, { 'operator': environment.With, 'alias': 'NoDischargeAntithrombotic', 'child': { 'operator': environment.ExpressionRef, 'reference': DischargedonAntithromboticTherapyReason_for_Not_Giving_Antithrombotic_at_Discharge }, 'suchThat': { 'operator': environment.InInterval, 'left': { 'operator': environment.Property, 'scope': 'NoDischargeAntithrombotic', 'path': 'authorDatetime', 'child': none }, 'right': { 'operator': environment.Property, 'scope': 'IschemicStrokeEncounter', 'path': 'relevantPeriod', 'child': none } } }], 'letClauseList': [] } }) }}{% endmacro %}
/*
	// DischargedonAntithromboticTherapy lines [61:1-63:100]
	define "Denominator Exceptions":
	  "Encounter with Documented Reason for No Antithrombotic At Discharge"
	    union "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge"
*/
{% macro DischargedonAntithromboticTherapyDenominator_Exceptions(environment, state) %}{{ environment.OperatorHandler.print(environment, environment.OperatorHandler, state, { 'operator': environment.ExpressionDef, 'context': 'Patient', 'name': 'Denominator Exceptions', 'child': { 'operator': environment.Union, 'children': [{ 'operator': environment.ExpressionRef, 'reference': DischargedonAntithromboticTherapyEncounter_with_Documented_Reason_for_No_Antithrombotic_At_Discharge }, { 'operator': environment.ExpressionRef, 'reference': DischargedonAntithromboticTherapyEncounter_with_Pharmacological_Contraindications_for_Antithrombotic_Therapy_at_Discharge }] } }) }}{% endmacro %}
