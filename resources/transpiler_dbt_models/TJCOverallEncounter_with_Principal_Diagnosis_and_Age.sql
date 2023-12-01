/*
define "Encounter with Principal Diagnosis and Age":
  "All Stroke Encounter" AllStrokeEncounter
    where AgeInYearsAt(date from start of AllStrokeEncounter.relevantPeriod)>= 18
*/
SELECT *
FROM
  (SELECT *
   FROM {{ ref('TJCOverallAll_Stroke_Encounter') }}) AS AllStrokeEncounter
WHERE (DATEDIFF(year,
                  (SELECT birthDatetime
                   FROM
                     (SELECT *
                      FROM ({{ transpilerRetrieve(none, 'qdm', 'patient', 'v56', 'None') }}) AS SingletonFrom
                      WHERE SingletonFrom.patientId = AllStrokeEncounter.patientId
                      LIMIT 1)), AllStrokeEncounter.relevantPeriod.low) >= 18)