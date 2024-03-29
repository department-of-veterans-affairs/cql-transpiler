/*
	// DischargedonAntithromboticTherapy lines [8:1-8:94]
	valueset "Antithrombotic Therapy for Ischemic Stroke": 'urn:oid:2.16.840.1.113762.1.4.1110.62'
*/
/*
	// DischargedonAntithromboticTherapy lines [9:1-9:58]
	valueset "Ethnicity": 'urn:oid:2.16.840.1.114222.4.11.837'
*/
/*
	// DischargedonAntithromboticTherapy lines [10:1-10:98]
	valueset "Medical Reason For Not Providing Treatment": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.473'
*/
/*
	// DischargedonAntithromboticTherapy lines [11:1-11:68]
	valueset "ONC Administrative Sex": 'urn:oid:2.16.840.1.113762.1.4.1'
*/
/*
	// DischargedonAntithromboticTherapy lines [12:1-12:70]
	valueset "Patient Refusal": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.93'
*/
/*
	// DischargedonAntithromboticTherapy lines [13:1-13:55]
	valueset "Payer": 'urn:oid:2.16.840.1.114222.4.11.3591'
*/
/*
	// DischargedonAntithromboticTherapy lines [14:1-14:112]
	valueset "Pharmacological Contraindications For Antithrombotic Therapy": 'urn:oid:2.16.840.1.113762.1.4.1110.52'
*/
/*
	// DischargedonAntithromboticTherapy lines [15:1-15:53]
	valueset "Race": 'urn:oid:2.16.840.1.114222.4.11.836'
*/
/*
	// DischargedonAntithromboticTherapy lines [17:1-17:15]
	context Patient
*/
SELECT * FROM Patient
/*
	// DischargedonAntithromboticTherapy lines [19:1-20:51]
	define "SDE Ethnicity":
	  ["Patient Characteristic Ethnicity": "Ethnicity"]
*/
SELECT * FROM urn:oid:2.16.840.1.114222.4.11.837
/*
	// DischargedonAntithromboticTherapy lines [22:1-23:43]
	define "SDE Payer":
	  ["Patient Characteristic Payer": "Payer"]
*/
SELECT * FROM urn:oid:2.16.840.1.114222.4.11.3591
/*
	// DischargedonAntithromboticTherapy lines [25:1-26:41]
	define "SDE Race":
	  ["Patient Characteristic Race": "Race"]
*/
SELECT * FROM urn:oid:2.16.840.1.114222.4.11.836
/*
	// DischargedonAntithromboticTherapy lines [28:1-29:58]
	define "SDE Sex":
	  ["Patient Characteristic Sex": "ONC Administrative Sex"]
*/
SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1
/*
	// DischargedonAntithromboticTherapy lines [31:1-32:33]
	define "Denominator":
	  TJC."Ischemic Stroke Encounter"
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE (((NonElectiveEncounter.relevantPeriod.high - NonElectiveEncounter.relevantPeriod.low) <= 120) AND (NonElectiveEncounter.relevantPeriod.high IN @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (IN Diagnosis.code OR IN Diagnosis.code))) AS AllStrokeEncounter) WHERE (floor(months_between(to_date(SELECT birthDatetime FROM SELECT * FROM Patient), SELECT low FROM AllStrokeEncounter.relevantPeriod) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (IN Diagnosis.code AND (Diagnosis.rank = 1)))
/*
	// DischargedonAntithromboticTherapy lines [34:1-35:50]
	define "Initial Population":
	  TJC."Encounter with Principal Diagnosis and Age"
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE (((NonElectiveEncounter.relevantPeriod.high - NonElectiveEncounter.relevantPeriod.low) <= 120) AND (NonElectiveEncounter.relevantPeriod.high IN @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (IN Diagnosis.code OR IN Diagnosis.code))) AS AllStrokeEncounter) WHERE (floor(months_between(to_date(SELECT birthDatetime FROM SELECT * FROM Patient), SELECT low FROM AllStrokeEncounter.relevantPeriod) / 12) >= 18)
/*
	// DischargedonAntithromboticTherapy lines [37:1-39:70]
	define "Denominator Exclusions":
	  TJC."Ischemic Stroke Encounters with Discharge Disposition"
	    union TJC."Encounter with Comfort Measures during Hospitalization"
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE (((NonElectiveEncounter.relevantPeriod.high - NonElectiveEncounter.relevantPeriod.low) <= 120) AND (NonElectiveEncounter.relevantPeriod.high IN @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (IN Diagnosis.code OR IN Diagnosis.code))) AS AllStrokeEncounter) WHERE (floor(months_between(to_date(SELECT birthDatetime FROM SELECT * FROM Patient), SELECT low FROM AllStrokeEncounter.relevantPeriod) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (IN Diagnosis.code AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter) WHERE ((((IN IschemicStrokeEncounter.dischargeDisposition OR IN IschemicStrokeEncounter.dischargeDisposition) OR IN IschemicStrokeEncounter.dischargeDisposition) OR IN IschemicStrokeEncounter.dischargeDisposition) OR IN IschemicStrokeEncounter.dischargeDisposition) UNION SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE (((NonElectiveEncounter.relevantPeriod.high - NonElectiveEncounter.relevantPeriod.low) <= 120) AND (NonElectiveEncounter.relevantPeriod.high IN @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (IN Diagnosis.code OR IN Diagnosis.code))) AS AllStrokeEncounter) WHERE (floor(months_between(to_date(SELECT birthDatetime FROM SELECT * FROM Patient), SELECT low FROM AllStrokeEncounter.relevantPeriod) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (IN Diagnosis.code AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WITH SELECT * FROM urn:oid:1.3.6.1.4.1.33895.1.3.0.45 AS /* TypeSpecifier operator: <ListTypeSpecifier> with children:[{Operator: _macros_sparksql.ChoiceTypeSpecifier with children: [ TypeSpecifier operator: <NamedTypeSpecifier> with children:[],  TypeSpecifier operator: <NamedTypeSpecifier> with children:[]]}]*//* Bad translation */ UNION SELECT * FROM urn:oid:1.3.6.1.4.1.33895.1.3.0.45 AS /* TypeSpecifier operator: <ListTypeSpecifier> with children:[{Operator: _macros_sparksql.ChoiceTypeSpecifier with children: [ TypeSpecifier operator: <NamedTypeSpecifier> with children:[],  TypeSpecifier operator: <NamedTypeSpecifier> with children:[]]}]*//* Bad translation */ AS (coalesce(SELECT low FROM IF (NOT ComfortMeasure.relevantDatetime IS NULL) THEN (/*{Operator: _macros_sparksql.Interval with children: [ComfortMeasure.relevantDatetime, ComfortMeasure.relevantDatetime]}*/) ELSE (IF (NOT ComfortMeasure.relevantPeriod IS NULL) THEN (ComfortMeasure.relevantPeriod) ELSE (NULL AS /* TypeSpecifier operator: <IntervalTypeSpecifier> with children:[ TypeSpecifier operator: <NamedTypeSpecifier> with children:[]]*//* Bad translation */)), ComfortMeasure.authorDatetime) IN LET SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1111.143 AS LastObs) WHERE ((LastObs.relevantPeriod.high IN /*{Operator: _macros_sparksql.Interval with children: [(Visit.relevantPeriod.low - {Operator: _macros_sparksql.Quantity with children: []}), Visit.relevantPeriod.low]}*/) AND NOT Visit.relevantPeriod.low IS NULL) ORDER BY relevantPeriod.high ASC, SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.292 AS LastED) WHERE ((LastED.relevantPeriod.high IN /*{Operator: _macros_sparksql.Interval with children: [(VisitStart - {Operator: _macros_sparksql.Quantity with children: []}), VisitStart]}*/) AND NOT VisitStart IS NULL) ORDER BY relevantPeriod.high ASCSELECT /*{Operator: _macros_sparksql.Interval with children: [coalesce(SELECT low FROM SELECT relevantPeriod FROM EDVisit, VisitStart), SELECT high FROM Visit.relevantPeriod]}*/ FROM (/*{Operator: _macros_sparksql.AliasRef with children: []}*/ AS Visit, coalesce(SELECT low FROM SELECT relevantPeriod FROM ObsVisit, SELECT low FROM Visit.relevantPeriod))))
/*
	// DischargedonAntithromboticTherapy lines [41:1-44:92]
	define "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Pharmacological Contraindications For Antithrombotic Therapy"] Pharmacological
	      such that Pharmacological.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE (((NonElectiveEncounter.relevantPeriod.high - NonElectiveEncounter.relevantPeriod.low) <= 120) AND (NonElectiveEncounter.relevantPeriod.high IN @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (IN Diagnosis.code OR IN Diagnosis.code))) AS AllStrokeEncounter) WHERE (floor(months_between(to_date(SELECT birthDatetime FROM SELECT * FROM Patient), SELECT low FROM AllStrokeEncounter.relevantPeriod) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (IN Diagnosis.code AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WITH SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.52 AS (Pharmacological.authorDatetime IN IschemicStrokeEncounter.relevantPeriod))
/*
	// DischargedonAntithromboticTherapy lines [46:1-49:73]
	define "Reason for Not Giving Antithrombotic at Discharge":
	  ["Medication, Not Discharged": "Antithrombotic Therapy for Ischemic Stroke"] NoAntithromboticDischarge
	    where NoAntithromboticDischarge.negationRationale in "Medical Reason For Not Providing Treatment"
	      or NoAntithromboticDischarge.negationRationale in "Patient Refusal"
*/
SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.62 AS NoAntithromboticDischarge) WHERE (IN NoAntithromboticDischarge.negationRationale OR IN NoAntithromboticDischarge.negationRationale)
/*
	// DischargedonAntithromboticTherapy lines [51:1-54:100]
	define "Numerator":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Antithrombotic Therapy for Ischemic Stroke"] DischargeAntithrombotic
	      such that DischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE (((NonElectiveEncounter.relevantPeriod.high - NonElectiveEncounter.relevantPeriod.low) <= 120) AND (NonElectiveEncounter.relevantPeriod.high IN @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (IN Diagnosis.code OR IN Diagnosis.code))) AS AllStrokeEncounter) WHERE (floor(months_between(to_date(SELECT birthDatetime FROM SELECT * FROM Patient), SELECT low FROM AllStrokeEncounter.relevantPeriod) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (IN Diagnosis.code AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WITH SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.62 AS (DischargeAntithrombotic.authorDatetime IN IschemicStrokeEncounter.relevantPeriod))
/*
	// DischargedonAntithromboticTherapy lines [56:1-59:102]
	define "Encounter with Documented Reason for No Antithrombotic At Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with "Reason for Not Giving Antithrombotic at Discharge" NoDischargeAntithrombotic
	      such that NoDischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE (((NonElectiveEncounter.relevantPeriod.high - NonElectiveEncounter.relevantPeriod.low) <= 120) AND (NonElectiveEncounter.relevantPeriod.high IN @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (IN Diagnosis.code OR IN Diagnosis.code))) AS AllStrokeEncounter) WHERE (floor(months_between(to_date(SELECT birthDatetime FROM SELECT * FROM Patient), SELECT low FROM AllStrokeEncounter.relevantPeriod) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (IN Diagnosis.code AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WITH SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.62 AS NoAntithromboticDischarge) WHERE (IN NoAntithromboticDischarge.negationRationale OR IN NoAntithromboticDischarge.negationRationale) AS (NoDischargeAntithrombotic.authorDatetime IN IschemicStrokeEncounter.relevantPeriod))
/*
	// DischargedonAntithromboticTherapy lines [61:1-63:100]
	define "Denominator Exceptions":
	  "Encounter with Documented Reason for No Antithrombotic At Discharge"
	    union "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge"
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE (((NonElectiveEncounter.relevantPeriod.high - NonElectiveEncounter.relevantPeriod.low) <= 120) AND (NonElectiveEncounter.relevantPeriod.high IN @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (IN Diagnosis.code OR IN Diagnosis.code))) AS AllStrokeEncounter) WHERE (floor(months_between(to_date(SELECT birthDatetime FROM SELECT * FROM Patient), SELECT low FROM AllStrokeEncounter.relevantPeriod) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (IN Diagnosis.code AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WITH SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.62 AS NoAntithromboticDischarge) WHERE (IN NoAntithromboticDischarge.negationRationale OR IN NoAntithromboticDischarge.negationRationale) AS (NoDischargeAntithrombotic.authorDatetime IN IschemicStrokeEncounter.relevantPeriod)) UNION SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE (((NonElectiveEncounter.relevantPeriod.high - NonElectiveEncounter.relevantPeriod.low) <= 120) AND (NonElectiveEncounter.relevantPeriod.high IN @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (IN Diagnosis.code OR IN Diagnosis.code))) AS AllStrokeEncounter) WHERE (floor(months_between(to_date(SELECT birthDatetime FROM SELECT * FROM Patient), SELECT low FROM AllStrokeEncounter.relevantPeriod) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (IN Diagnosis.code AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WITH SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.52 AS (Pharmacological.authorDatetime IN IschemicStrokeEncounter.relevantPeriod))