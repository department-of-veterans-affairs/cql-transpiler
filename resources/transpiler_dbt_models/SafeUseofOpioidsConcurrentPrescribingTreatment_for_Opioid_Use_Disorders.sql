/*
define "Treatment for Opioid Use Disorders":
  ( ["Medication, Active": "Medications for Opioid Use Disorder (MOUD)"]
    union ["Medication, Order": "Medications for Opioid Use Disorder (MOUD)"] ) MedicationTreatment
    with ["Intervention, Performed": "Opioid Medication Assisted Treatment (MAT)"] MAT
      such that Coalesce(start of Global."NormalizeInterval"(MedicationTreatment.relevantDatetime, MedicationTreatment.relevantPeriod), MedicationTreatment.authorDatetime) during day of Global."NormalizeInterval" ( MAT.relevantDatetime, MAT.relevantPeriod )
        and Coalesce(start of Global."NormalizeInterval"(MedicationTreatment.relevantDatetime, MedicationTreatment.relevantPeriod), MedicationTreatment.authorDatetime) during day of "Measurement Period"
*/ WITH _MedicationTreatment AS
  (SELECT *
   FROM
     (SELECT *
      FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1046.269', 'qdm', 'medication_active', 'v56', 'code') }})) NATURAL
   FULL JOIN
     (SELECT *
      FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1046.269', 'qdm', 'medication_order', 'v56', 'code') }}))),
        MAT AS ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1111.177', 'qdm', 'intervention_performed', 'v56', 'code') }})
SELECT *
FROM
  (SELECT MedicationTreatment.*
   FROM _MedicationTreatment AS MedicationTreatment
   LEFT JOIN MAT USING (patientId)
   WHERE ((coalesce(IF(NOT MedicationTreatment.relevantDatetime IS NULL, struct(MedicationTreatment.relevantDatetime AS low, MedicationTreatment.relevantDatetime AS high), IF(NOT MedicationTreatment.relevantPeriod IS NULL, MedicationTreatment.relevantPeriod, NULL)).low, MedicationTreatment.authorDatetime) BETWEEN IF(NOT MAT.relevantDatetime IS NULL, struct(MAT.relevantDatetime AS low, MAT.relevantDatetime AS high), IF(NOT MAT.relevantPeriod IS NULL, MAT.relevantPeriod, NULL)).low AND IF(NOT MAT.relevantDatetime IS NULL, struct(MAT.relevantDatetime AS low, MAT.relevantDatetime AS high), IF(NOT MAT.relevantPeriod IS NULL, MAT.relevantPeriod, NULL)).high)
          AND (coalesce(IF(NOT MedicationTreatment.relevantDatetime IS NULL, struct(MedicationTreatment.relevantDatetime AS low, MedicationTreatment.relevantDatetime AS high), IF(NOT MedicationTreatment.relevantPeriod IS NULL, MedicationTreatment.relevantPeriod, NULL)).low, MedicationTreatment.authorDatetime) BETWEEN _parameters.measurementPeriod.low AND _parameters.measurementPeriod.high)))