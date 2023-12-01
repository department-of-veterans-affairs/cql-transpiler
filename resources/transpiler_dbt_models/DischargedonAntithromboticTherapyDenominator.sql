/*
define "Denominator":
  TJC."Ischemic Stroke Encounter"
*/
SELECT *
FROM {{ ref('TJCOverallIschemic_Stroke_Encounter') }}