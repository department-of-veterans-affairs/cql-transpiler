/*
define "Ischemic Stroke Encounter":
  "Encounter with Principal Diagnosis and Age" EncounterWithAge
    where exists ( EncounterWithAge.diagnoses Diagnosis
        where Diagnosis.code in "Ischemic Stroke"
          and Diagnosis.rank = 1
    )
*/
SELECT *
FROM
  (SELECT *
   FROM {{ ref('TJCOverallEncounter_with_Principal_Diagnosis_and_Age') }}) AS EncounterWithAge
WHERE EXISTS
    (SELECT *
     FROM
       (/* warning -- speculative coercion follows -- SIMPLE to QUERY/TABLE */SELECT col.*
        FROM (explode(EncounterWithAge.diagnoses))) AS Diagnosis
     WHERE (EXISTS
              (SELECT codes.code AS code
               FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.247', none) }})
               WHERE code = Diagnosis.code)
            AND (Diagnosis.rank = 1)))