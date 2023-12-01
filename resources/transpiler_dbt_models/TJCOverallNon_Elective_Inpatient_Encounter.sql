/*
define "Non Elective Inpatient Encounter":
  ["Encounter, Performed": "Nonelective Inpatient Encounter"] NonElectiveEncounter
    where Global."LengthInDays" ( NonElectiveEncounter.relevantPeriod ) <= 120
      and NonElectiveEncounter.relevantPeriod ends during day of "Measurement Period"
*/
SELECT *
FROM ({{ transpilerRetrieve('2.16.840.1.113883.3.117.1.7.1.424', 'qdm', 'encounter_performed', 'v56', 'code') }}) AS NonElectiveEncounter
WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120)
       AND (NonElectiveEncounter.relevantPeriod.high BETWEEN _parameters.measurementPeriod.low AND _parameters.measurementPeriod.high))