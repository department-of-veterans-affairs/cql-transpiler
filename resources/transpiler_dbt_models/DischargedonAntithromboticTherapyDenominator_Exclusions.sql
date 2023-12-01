/*
define "Denominator Exclusions":
  TJC."Ischemic Stroke Encounters with Discharge Disposition"
    union TJC."Encounter with Comfort Measures during Hospitalization"
*/
  (SELECT *
   FROM {{ ref('TJCOverallIschemic_Stroke_Encounters_with_Discharge_Disposition') }})
UNION
  (SELECT *
   FROM {{ ref('TJCOverallEncounter_with_Comfort_Measures_during_Hospitalization') }})