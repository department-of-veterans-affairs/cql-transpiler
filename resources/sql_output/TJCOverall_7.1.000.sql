
/*
	// TJCOverall lines [7:1-7:65]
	valueset "Comfort Measures": 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45' 
*/
/*
	// TJCOverall lines [8:1-8:87]
	valueset "Discharge To Acute Care Facility": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.87' 
*/
/*
	// TJCOverall lines [9:1-9:107]
	valueset "Discharged to Health Care Facility for Hospice Care": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.207' 
*/
/*
	// TJCOverall lines [10:1-10:91]
	valueset "Discharged to Home for Hospice Care": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.209' 
*/
/*
	// TJCOverall lines [11:1-11:82]
	valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292' 
*/
/*
	// TJCOverall lines [12:1-12:74]
	valueset "Hemorrhagic Stroke": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.212' 
*/
/*
	// TJCOverall lines [13:1-13:71]
	valueset "Ischemic Stroke": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.247' 
*/
/*
	// TJCOverall lines [14:1-14:83]
	valueset "Left Against Medical Advice": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.308' 
*/
/*
	// TJCOverall lines [15:1-15:87]
	valueset "Nonelective Inpatient Encounter": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424' 
*/
/*
	// TJCOverall lines [16:1-16:73]
	valueset "Observation Services": 'urn:oid:2.16.840.1.113762.1.4.1111.143' 
*/
/*
	// TJCOverall lines [17:1-17:71]
	valueset "Patient Expired": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.309' 
*/
/*
	// TJCOverall lines [21:1-21:15]
	context Patient
*/
/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': None, 'codeComparator': None, 'codeProperty': None, 'child': None, 'valueSet': None} with children: [*//*] */
/*
	// TJCOverall lines [23:1-25:57]
	define "Intervention Comfort Measures":
	  ["Intervention, Order": "Comfort Measures"]
	    union ["Intervention, Performed": "Comfort Measures"]
*/
/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionOrder', 'resultTypeLabel': 'Intervention, Order', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45'}} with children: [*//*] */ UNION /* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionPerformed', 'resultTypeLabel': 'Intervention, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45'}} with children: [*//*] */
/*
	// TJCOverall lines [58:1-61:85]
	define "Non Elective Inpatient Encounter":
	  ["Encounter, Performed": "Nonelective Inpatient Encounter"] NonElectiveEncounter
	    where Global."LengthInDays" ( NonElectiveEncounter.relevantPeriod ) <= 120
	      and NonElectiveEncounter.relevantPeriod ends during day of "Measurement Period"
*/
SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424'}} with children: [*//*] */ AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod., NonElectiveEncounter.relevantPeriod.) <= 120) AND NonElectiveEncounter.relevantPeriod. BETWEEN SELECT  FROM @Measurement_Period AND SELECT  FROM @Measurement_Period)
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
SELECT * FROM (SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424'}} with children: [*//*] */ AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod., NonElectiveEncounter.relevantPeriod.) <= 120) AND NonElectiveEncounter.relevantPeriod. BETWEEN SELECT  FROM @Measurement_Period AND SELECT  FROM @Measurement_Period) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/)))
/*
	// TJCOverall lines [63:1-65:81]
	define "Encounter with Principal Diagnosis and Age":
	  "All Stroke Encounter" AllStrokeEncounter
	    where AgeInYearsAt(date from start of AllStrokeEncounter.relevantPeriod)>= 18
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424'}} with children: [*//*] */ AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod., NonElectiveEncounter.relevantPeriod.) <= 120) AND NonElectiveEncounter.relevantPeriod. BETWEEN SELECT  FROM @Measurement_Period AND SELECT  FROM @Measurement_Period) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod., to_date(/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': None, 'codeComparator': None, 'codeProperty': None, 'child': None, 'valueSet': None} with children: [*//*] */.birthDatetime)) / 12) >= 18)
/*
	// TJCOverall lines [37:1-42:5]
	define "Ischemic Stroke Encounter":
	  "Encounter with Principal Diagnosis and Age" EncounterWithAge
	    where exists ( EncounterWithAge.diagnoses Diagnosis
	        where Diagnosis.code in "Ischemic Stroke"
	          and Diagnosis.rank = 1
	    )
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424'}} with children: [*//*] */ AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod., NonElectiveEncounter.relevantPeriod.) <= 120) AND NonElectiveEncounter.relevantPeriod. BETWEEN SELECT  FROM @Measurement_Period AND SELECT  FROM @Measurement_Period) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod., to_date(/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': None, 'codeComparator': None, 'codeProperty': None, 'child': None, 'valueSet': None} with children: [*//*] */.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1)))
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
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424'}} with children: [*//*] */ AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod., NonElectiveEncounter.relevantPeriod.) <= 120) AND NonElectiveEncounter.relevantPeriod. BETWEEN SELECT  FROM @Measurement_Period AND SELECT  FROM @Measurement_Period) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod., to_date(/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': None, 'codeComparator': None, 'codeProperty': None, 'child': None, 'valueSet': None} with children: [*//*] */.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter) WHERE ((((/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.87>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.308>*/) OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.309>*/) OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.209>*/) OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.207>*/)
/*
	// TJCOverall lines [53:1-56:230]
	define "Encounter with Comfort Measures during Hospitalization":
	  "Ischemic Stroke Encounter" IschemicStrokeEncounter
	    with "Intervention Comfort Measures" ComfortMeasure
	      such that Coalesce(start of Global."NormalizeInterval"(ComfortMeasure.relevantDatetime, ComfortMeasure.relevantPeriod), ComfortMeasure.authorDatetime)during Global."HospitalizationWithObservation" ( IschemicStrokeEncounter )
*/
SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424'}} with children: [*//*] */ AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod., NonElectiveEncounter.relevantPeriod.) <= 120) AND NonElectiveEncounter.relevantPeriod. BETWEEN SELECT  FROM @Measurement_Period AND SELECT  FROM @Measurement_Period) AS NonElectiveEncounter) WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses AS Diagnosis) WHERE ((Diagnosis.rank = 1) AND (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.212>*/ OR /* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/))) AS AllStrokeEncounter) WHERE (floor(months_between(AllStrokeEncounter.relevantPeriod., to_date(/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'Patient', 'resultTypeLabel': None, 'codeComparator': None, 'codeProperty': None, 'child': None, 'valueSet': None} with children: [*//*] */.birthDatetime)) / 12) >= 18) AS EncounterWithAge) WHERE EXISTS (SELECT * FROM (EncounterWithAge.diagnoses AS Diagnosis) WHERE (/* Unsupported InValueSet valueSetReference: <urn:oid:2.16.840.1.113883.3.117.1.7.1.247>*/ AND (Diagnosis.rank = 1))) AS IschemicStrokeEncounter, WHERE (LET /* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionOrder', 'resultTypeLabel': 'Intervention, Order', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45'}} with children: [*//*] */ UNION /* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveInterventionPerformed', 'resultTypeLabel': 'Intervention, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45'}} with children: [*//*] */ AS ComfortMeasure SELECT coalesce(IF (NOT ComfortMeasure.relevantDatetime AS  SUCH THAT ) THEN (SELECT struct(ComfortMeasure.relevantDatetime as , ComfortMeasure.relevantDatetime as ) _val) ELSE (IF (NOT ComfortMeasure.relevantPeriod AS  SUCH THAT ) THEN (ComfortMeasure.relevantPeriod) ELSE (NULL))., ComfortMeasure.authorDatetime) BETWEEN SELECT  FROM LET SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113762.1.4.1111.143'}} with children: [*//*] */ AS LastObs) WHERE (LastObs.relevantPeriod. BETWEEN SELECT  FROM SELECT struct(Visit.relevantPeriod. as , (Visit.relevantPeriod. - INTERVAL 1 HOUR) as ) _val AND SELECT  FROM SELECT struct(Visit.relevantPeriod. as , (Visit.relevantPeriod. - INTERVAL 1 HOUR) as ) _val AND NOT Visit.relevantPeriod. AS  SUCH THAT ) ORDER BY relevantPeriod. ASC, SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'}} with children: [*//*] */ AS LastED) WHERE (LastED.relevantPeriod. BETWEEN SELECT  FROM SELECT struct(VisitStart as , (VisitStart - INTERVAL 1 HOUR) as ) _val AND SELECT  FROM SELECT struct(VisitStart as , (VisitStart - INTERVAL 1 HOUR) as ) _val AND NOT VisitStart AS  SUCH THAT ) ORDER BY relevantPeriod. ASCSELECT SELECT struct(Visit.relevantPeriod. as , coalesce(EDVisit.relevantPeriod., VisitStart) as ) _val FROM (IschemicStrokeEncounter AS Visit, coalesce(ObsVisit.relevantPeriod., Visit.relevantPeriod.)) AND SELECT  FROM LET SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113762.1.4.1111.143'}} with children: [*//*] */ AS LastObs) WHERE (LastObs.relevantPeriod. BETWEEN SELECT  FROM SELECT struct(Visit.relevantPeriod. as , (Visit.relevantPeriod. - INTERVAL 1 HOUR) as ) _val AND SELECT  FROM SELECT struct(Visit.relevantPeriod. as , (Visit.relevantPeriod. - INTERVAL 1 HOUR) as ) _val AND NOT Visit.relevantPeriod. AS  SUCH THAT ) ORDER BY relevantPeriod. ASC, SELECT * FROM (/* Unimplemented Operator: <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}> with arguments: {'operator': <Namespace {'defaultDataType': 'table', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printUnimplemented'>}>, 'modelType': 'urn:healthit-gov:qdm:v5_6', 'templateId': 'PositiveEncounterPerformed', 'resultTypeLabel': 'Encounter, Performed', 'codeComparator': 'in', 'codeProperty': 'code', 'child': None, 'valueSet': {'operator': <Namespace {'defaultDataType': 'simple', 'getDataType': <Macro 'operatorClassGetDataType'>, 'print': <Macro 'printValueSetRef'>}>, 'reference': 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'}} with children: [*//*] */ AS LastED) WHERE (LastED.relevantPeriod. BETWEEN SELECT  FROM SELECT struct(VisitStart as , (VisitStart - INTERVAL 1 HOUR) as ) _val AND SELECT  FROM SELECT struct(VisitStart as , (VisitStart - INTERVAL 1 HOUR) as ) _val AND NOT VisitStart AS  SUCH THAT ) ORDER BY relevantPeriod. ASCSELECT SELECT struct(Visit.relevantPeriod. as , coalesce(EDVisit.relevantPeriod., VisitStart) as ) _val FROM (IschemicStrokeEncounter AS Visit, coalesce(ObsVisit.relevantPeriod., Visit.relevantPeriod.)) _val))
/*
	// TJCOverall lines [67:1-68:81]
	define function "HospitalizationWithObservationLengthofStay"(Encounter "Encounter, Performed" ):
	  Global."LengthInDays" ( Global."HospitalizationWithObservation" ( Encounter ) )
*/
/*
	// TJCOverall lines [70:1-71:100]
	define function "TruncateTime"(Value DateTime ):
	  DateTime(year from Value, month from Value, day from Value, 0, 0, 0, 0, timezoneoffset from Value)
*/
/*
	// TJCOverall lines [73:1-74:75]
	define function "CalendarDayOfOrDayAfter"(StartValue DateTime ):
	  Interval["TruncateTime"(StartValue), "TruncateTime"(StartValue + 2 days))
*/
