/*
define "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge":
  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
    with ["Medication, Discharge": "Pharmacological Contraindications For Antithrombotic Therapy"] Pharmacological
      such that Pharmacological.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/ WITH _IschemicStrokeEncounter AS
  (SELECT *
   FROM {{ ref('TJCOverallIschemic_Stroke_Encounter') }}),
        Pharmacological AS ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1110.52', 'qdm', 'medication_discharge', 'v56', 'code') }})
SELECT *
FROM
  (SELECT IschemicStrokeEncounter.*
   FROM _IschemicStrokeEncounter AS IschemicStrokeEncounter
   LEFT JOIN Pharmacological USING (patientId)
   WHERE (Pharmacological.authorDatetime BETWEEN IschemicStrokeEncounter.relevantPeriod.low AND IschemicStrokeEncounter.relevantPeriod.high))