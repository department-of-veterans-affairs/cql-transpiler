/*
define "Denominator Exceptions":
  "Encounter with Documented Reason for No Antithrombotic At Discharge"
    union "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge"
*/
  (SELECT *
   FROM {{ ref('DischargedonAntithromboticTherapyEncounter_with_Documented_Reason_for_No_Antithrombotic_At_Discharge') }})
UNION
  (SELECT *
   FROM {{ ref('DischargedonAntithromboticTherapyEncounter_with_Pharmacological_Contraindications_for_Antithrombotic_Therapy_at_Discharge') }})