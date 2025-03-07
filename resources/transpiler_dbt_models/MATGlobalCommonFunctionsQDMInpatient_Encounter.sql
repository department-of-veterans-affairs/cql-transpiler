/*
define "Inpatient Encounter":
  ["Encounter, Performed": "Encounter Inpatient"] EncounterInpatient
    where EncounterInpatient.relevantPeriod ends during day of "Measurement Period"
*/
SELECT *
FROM ({{ transpilerRetrieve('2.16.840.1.113883.3.666.5.307', 'qdm', 'encounter_performed', 'v56', 'code') }}) AS EncounterInpatient
WHERE (EncounterInpatient.relevantPeriod.high BETWEEN _parameters.measurementPeriod.low AND _parameters.measurementPeriod.high)