/*
define "Numerator":
  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
    with ["Medication, Discharge": "Antithrombotic Therapy for Ischemic Stroke"] DischargeAntithrombotic
      such that DischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/ WITH _IschemicStrokeEncounter AS
  (SELECT *
   FROM {{ ref('TJCOverallIschemic_Stroke_Encounter') }}),
        DischargeAntithrombotic AS ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1110.62', 'qdm', 'medication_discharge', 'v56', 'code') }})
SELECT *
FROM
  (SELECT IschemicStrokeEncounter.*
   FROM _IschemicStrokeEncounter AS IschemicStrokeEncounter
   LEFT JOIN DischargeAntithrombotic USING (patientId)
   WHERE (DischargeAntithrombotic.authorDatetime BETWEEN IschemicStrokeEncounter.relevantPeriod.low AND IschemicStrokeEncounter.relevantPeriod.high))