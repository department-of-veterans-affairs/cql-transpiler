/*
define "All Stroke Encounter":
  "Non Elective Inpatient Encounter" NonElectiveEncounter
    where exists ( NonElectiveEncounter.diagnoses Diagnosis
        where Diagnosis.rank = 1
          and ( Diagnosis.code in "Hemorrhagic Stroke"
              or Diagnosis.code in "Ischemic Stroke"
          )
    )
*/
SELECT *
FROM
  (SELECT *
   FROM {{ ref('TJCOverallNon_Elective_Inpatient_Encounter') }}) AS NonElectiveEncounter
WHERE EXISTS
    (SELECT *
     FROM
       (/* warning -- speculative coercion follows -- SIMPLE to QUERY/TABLE */SELECT col.*
        FROM (explode(NonElectiveEncounter.diagnoses))) AS Diagnosis
     WHERE ((Diagnosis.rank = 1)
            AND (EXISTS
                   (SELECT codes.code AS code
                    FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.212', none) }})
                    WHERE code = Diagnosis.code)
                 OR EXISTS
                   (SELECT codes.code AS code
                    FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.247', none) }})
                    WHERE code = Diagnosis.code))))