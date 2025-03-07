/*
define "Inpatient Encounter with Age Greater than or Equal to 18":
  Global."Inpatient Encounter" InpatientHospitalEncounter
    where AgeInYearsAt(date from start of InpatientHospitalEncounter.relevantPeriod) >= 18
*/
SELECT *
FROM
  (SELECT *
   FROM {{ ref('MATGlobalCommonFunctionsQDMInpatient_Encounter') }}) AS InpatientHospitalEncounter
WHERE (DATEDIFF(year,
                  (SELECT birthDatetime
                   FROM
                     (SELECT *
                      FROM ({{ transpilerRetrieve(none, 'qdm', 'patient', 'v56', 'None') }}) AS SingletonFrom
                      WHERE SingletonFrom.patientId = InpatientHospitalEncounter.patientId
                      LIMIT 1)), InpatientHospitalEncounter.relevantPeriod.low) >= 18)