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
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN (SELECT low FROM @Measurement_Period, SELECT high FROM @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod.low, to_date(SELECT * FROM Patient.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1)))
/*
	// DischargedonAntithromboticTherapy lines [34:1-35:50]
	define "Initial Population":
	  TJC."Encounter with Principal Diagnosis and Age"
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN (SELECT low FROM @Measurement_Period, SELECT high FROM @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod.low, to_date(SELECT * FROM Patient.birthDatetime)) / 12) >= 18)
/*
	// DischargedonAntithromboticTherapy lines [37:1-39:70]
	define "Denominator Exclusions":
	  TJC."Ischemic Stroke Encounters with Discharge Disposition"
	    union TJC."Encounter with Comfort Measures during Hospitalization"
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN (SELECT low FROM @Measurement_Period, SELECT high FROM @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod.low, to_date(SELECT * FROM Patient.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter) WHERE ((((/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.87>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.308>*/) OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.309>*/) OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.209>*/) OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.207>*/) UNION SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN (SELECT low FROM @Measurement_Period, SELECT high FROM @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod.low, to_date(SELECT * FROM Patient.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WHERE (LET SELECT * FROM urn:oid:1.3.6.1.4.1.33895.1.3.0.45 UNION SELECT * FROM urn:oid:1.3.6.1.4.1.33895.1.3.0.45 AS ComfortMeasure SELECT coalesce(IF (NOT ComfortMeasure.relevantDatetime AS  SUCH THAT ) THEN (SELECT struct(ComfortMeasure.relevantDatetime as high, ComfortMeasure.relevantDatetime as low) _val) ELSE (IF (NOT ComfortMeasure.relevantPeriod AS  SUCH THAT ) THEN (ComfortMeasure.relevantPeriod) ELSE (NULL)).low, ComfortMeasure.authorDatetime) BETWEEN (SELECT low FROM LET SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1111.143 AS LastObs) WHERE (LastObs.relevantPeriod.high BETWEEN (SELECT low FROM SELECT struct(Visit.relevantPeriod.low as high, (Visit.relevantPeriod.low - INTERVAL 1 HOUR) as low) _val, SELECT high FROM SELECT struct(Visit.relevantPeriod.low as high, (Visit.relevantPeriod.low - INTERVAL 1 HOUR) as low) _val) AND NOT Visit.relevantPeriod.low AS  SUCH THAT ) ORDER BY relevantPeriod.high ASC, SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.292 AS LastED) WHERE (LastED.relevantPeriod.high BETWEEN (SELECT low FROM SELECT struct(VisitStart as high, (VisitStart - INTERVAL 1 HOUR) as low) _val, SELECT high FROM SELECT struct(VisitStart as high, (VisitStart - INTERVAL 1 HOUR) as low) _val) AND NOT VisitStart AS  SUCH THAT ) ORDER BY relevantPeriod.high ASCSELECT SELECT struct(Visit.relevantPeriod.high as high, coalesce(EDVisit.relevantPeriod.low, VisitStart) as low) _val FROM (IschemicStrokeEncounter AS Visit, coalesce(ObsVisit.relevantPeriod.low, Visit.relevantPeriod.low)), SELECT high FROM LET SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1111.143 AS LastObs) WHERE (LastObs.relevantPeriod.high BETWEEN (SELECT low FROM SELECT struct(Visit.relevantPeriod.low as high, (Visit.relevantPeriod.low - INTERVAL 1 HOUR) as low) _val, SELECT high FROM SELECT struct(Visit.relevantPeriod.low as high, (Visit.relevantPeriod.low - INTERVAL 1 HOUR) as low) _val) AND NOT Visit.relevantPeriod.low AS  SUCH THAT ) ORDER BY relevantPeriod.high ASC, SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.292 AS LastED) WHERE (LastED.relevantPeriod.high BETWEEN (SELECT low FROM SELECT struct(VisitStart as high, (VisitStart - INTERVAL 1 HOUR) as low) _val, SELECT high FROM SELECT struct(VisitStart as high, (VisitStart - INTERVAL 1 HOUR) as low) _val) AND NOT VisitStart AS  SUCH THAT ) ORDER BY relevantPeriod.high ASCSELECT SELECT struct(Visit.relevantPeriod.high as high, coalesce(EDVisit.relevantPeriod.low, VisitStart) as low) _val FROM (IschemicStrokeEncounter AS Visit, coalesce(ObsVisit.relevantPeriod.low, Visit.relevantPeriod.low))) _val))
/*
	// DischargedonAntithromboticTherapy lines [41:1-44:92]
	define "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Pharmacological Contraindications For Antithrombotic Therapy"] Pharmacological
	      such that Pharmacological.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN (SELECT low FROM @Measurement_Period, SELECT high FROM @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod.low, to_date(SELECT * FROM Patient.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WHERE (LET SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.52 AS Pharmacological SELECT Pharmacological.authorDatetime BETWEEN (SELECT low FROM IschemicStrokeEncounter.relevantPeriod, SELECT high FROM IschemicStrokeEncounter.relevantPeriod) _val))
/*
	// DischargedonAntithromboticTherapy lines [46:1-49:73]
	define "Reason for Not Giving Antithrombotic at Discharge":
	  ["Medication, Not Discharged": "Antithrombotic Therapy for Ischemic Stroke"] NoAntithromboticDischarge
	    where NoAntithromboticDischarge.negationRationale in "Medical Reason For Not Providing Treatment"
	      or NoAntithromboticDischarge.negationRationale in "Patient Refusal"
*/
SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.62 AS NoAntithromboticDischarge) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.473>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.93>*/)
/*
	// DischargedonAntithromboticTherapy lines [51:1-54:100]
	define "Numerator":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with ["Medication, Discharge": "Antithrombotic Therapy for Ischemic Stroke"] DischargeAntithrombotic
	      such that DischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN (SELECT low FROM @Measurement_Period, SELECT high FROM @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod.low, to_date(SELECT * FROM Patient.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WHERE (LET SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.62 AS DischargeAntithrombotic SELECT DischargeAntithrombotic.authorDatetime BETWEEN (SELECT low FROM IschemicStrokeEncounter.relevantPeriod, SELECT high FROM IschemicStrokeEncounter.relevantPeriod) _val))
/*
	// DischargedonAntithromboticTherapy lines [56:1-59:102]
	define "Encounter with Documented Reason for No Antithrombotic At Discharge":
	  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with "Reason for Not Giving Antithrombotic at Discharge" NoDischargeAntithrombotic
	      such that NoDischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN (SELECT low FROM @Measurement_Period, SELECT high FROM @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod.low, to_date(SELECT * FROM Patient.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WHERE (LET SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.62 AS NoAntithromboticDischarge) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.473>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.93>*/) AS NoDischargeAntithrombotic SELECT NoDischargeAntithrombotic.authorDatetime BETWEEN (SELECT low FROM IschemicStrokeEncounter.relevantPeriod, SELECT high FROM IschemicStrokeEncounter.relevantPeriod) _val))
/*
	// DischargedonAntithromboticTherapy lines [61:1-63:100]
	define "Denominator Exceptions":
	  "Encounter with Documented Reason for No Antithrombotic At Discharge"
	    union "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge"
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN (SELECT low FROM @Measurement_Period, SELECT high FROM @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod.low, to_date(SELECT * FROM Patient.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WHERE (LET SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.62 AS NoAntithromboticDischarge) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.473>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.93>*/) AS NoDischargeAntithrombotic SELECT NoDischargeAntithrombotic.authorDatetime BETWEEN (SELECT low FROM IschemicStrokeEncounter.relevantPeriod, SELECT high FROM IschemicStrokeEncounter.relevantPeriod) _val)) UNION SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN (SELECT low FROM @Measurement_Period, SELECT high FROM @Measurement_Period)) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod.low, to_date(SELECT * FROM Patient.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WHERE (LET SELECT * FROM urn:oid:2.16.840.1.113762.1.4.1110.52 AS Pharmacological SELECT Pharmacological.authorDatetime BETWEEN (SELECT low FROM IschemicStrokeEncounter.relevantPeriod, SELECT high FROM IschemicStrokeEncounter.relevantPeriod) _val))