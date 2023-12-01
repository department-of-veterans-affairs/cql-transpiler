/*
define "Reason for Not Giving Antithrombotic at Discharge":
  ["Medication, Not Discharged": "Antithrombotic Therapy for Ischemic Stroke"] NoAntithromboticDischarge
    where NoAntithromboticDischarge.negationRationale in "Medical Reason For Not Providing Treatment"
      or NoAntithromboticDischarge.negationRationale in "Patient Refusal"
*/
SELECT *
FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1110.62', 'qdm', 'medication_not_discharged', 'v56', 'code') }}) AS NoAntithromboticDischarge
WHERE (EXISTS
         (SELECT codes.code AS code
          FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.473', none) }})
          WHERE code = NoAntithromboticDischarge.negationRationale)
       OR EXISTS
         (SELECT codes.code AS code
          FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.93', none) }})
          WHERE code = NoAntithromboticDischarge.negationRationale))