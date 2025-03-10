/*
define "Initial Population":
  TJC."Encounter with Principal Diagnosis and Age"
*/
SELECT *
FROM {{ ref('TJCOverallEncounter_with_Principal_Diagnosis_and_Age') }}