/*
define "Ischemic Stroke Encounters with Discharge Disposition":
  ( ( "Ischemic Stroke Encounter" IschemicStrokeEncounter
        where IschemicStrokeEncounter.dischargeDisposition in "Discharge To Acute Care Facility"
          or IschemicStrokeEncounter.dischargeDisposition in "Left Against Medical Advice"
          or IschemicStrokeEncounter.dischargeDisposition in "Patient Expired"
          or IschemicStrokeEncounter.dischargeDisposition in "Discharged to Home for Hospice Care"
          or IschemicStrokeEncounter.dischargeDisposition in "Discharged to Health Care Facility for Hospice Care"
    )
  )
*/
SELECT *
FROM
  (SELECT *
   FROM {{ ref('TJCOverallIschemic_Stroke_Encounter') }}) AS IschemicStrokeEncounter
WHERE ((((EXISTS
            (SELECT codes.code AS code
             FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.87', none) }})
             WHERE code = IschemicStrokeEncounter.dischargeDisposition)
          OR EXISTS
            (SELECT codes.code AS code
             FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.308', none) }})
             WHERE code = IschemicStrokeEncounter.dischargeDisposition))
         OR EXISTS
           (SELECT codes.code AS code
            FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.309', none) }})
            WHERE code = IschemicStrokeEncounter.dischargeDisposition))
        OR EXISTS
          (SELECT codes.code AS code
           FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.209', none) }})
           WHERE code = IschemicStrokeEncounter.dischargeDisposition))
       OR EXISTS
         (SELECT codes.code AS code
          FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.207', none) }})
          WHERE code = IschemicStrokeEncounter.dischargeDisposition))