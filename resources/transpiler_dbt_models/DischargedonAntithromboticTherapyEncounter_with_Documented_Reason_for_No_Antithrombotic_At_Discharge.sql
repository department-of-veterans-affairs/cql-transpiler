/*
define "Encounter with Documented Reason for No Antithrombotic At Discharge":
  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
    with "Reason for Not Giving Antithrombotic at Discharge" NoDischargeAntithrombotic
      such that NoDischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod
*/ WITH _IschemicStrokeEncounter AS
  (SELECT *
   FROM {{ ref('TJCOverallIschemic_Stroke_Encounter') }}),
        NoDischargeAntithrombotic AS
  (SELECT *
   FROM {{ ref('DischargedonAntithromboticTherapyReason_for_Not_Giving_Antithrombotic_at_Discharge') }})
SELECT *
FROM
  (SELECT IschemicStrokeEncounter.*
   FROM _IschemicStrokeEncounter AS IschemicStrokeEncounter
   LEFT JOIN NoDischargeAntithrombotic USING (patientId)
   WHERE (NoDischargeAntithrombotic.authorDatetime BETWEEN IschemicStrokeEncounter.relevantPeriod.low AND IschemicStrokeEncounter.relevantPeriod.high))