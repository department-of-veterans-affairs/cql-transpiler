/*
define "Denominator Exclusion":
  /*Excludes encounters of patients with cancer pain or who are receiving palliative or hospice care at the time of the encounter or who are receiving medication for opioid use disorder, have sickle cell disease, or who are discharged to another inpatient care facility or discharged against medical advice, or expire during the inpatient stay*/ "Inpatient Encounters with an Opioid or Benzodiazepine at Discharge" InpatientEncounter
where exists (["Diagnosis": "Cancer Related Pain"] Cancer
              where Cancer.prevalencePeriod overlaps day of InpatientEncounter.relevantPeriod )
  or exists (["Diagnosis": "Sickle Cell Disease with and without Crisis"] SickleCellDisease
             where SickleCellDisease.prevalencePeriod overlaps InpatientEncounter.relevantPeriod )
  or exists (InpatientEncounter.diagnoses Diagnosis
             where Diagnosis.code in "Cancer Related Pain" )
  or exists (["Diagnosis": "Opioid Use Disorder"] OUD
             where
               start of OUD.prevalencePeriod
               before day of end of InpatientEncounter.relevantPeriod )
  or exists ("Treatment for Opioid Use Disorders" OUDTreatment
             where Coalesce
                 (start of Global."NormalizeInterval"(OUDTreatment.relevantDatetime, OUDTreatment.relevantPeriod), OUDTreatment.authorDatetime) during day of InpatientEncounter.relevantPeriod )
  or exists ("Intervention Palliative or Hospice Care" PalliativeOrHospiceCare
             where Coalesce
                 (start of Global."NormalizeInterval"(PalliativeOrHospiceCare.relevantDatetime, PalliativeOrHospiceCare.relevantPeriod), PalliativeOrHospiceCare.authorDatetime) during Global."HospitalizationWithObservation" (InpatientEncounter) )
  or (InpatientEncounter.dischargeDisposition in "Discharge To Acute Care Facility"
      or InpatientEncounter.dischargeDisposition in "Hospice Care Referral or Admission"
      or InpatientEncounter.dischargeDisposition in "Patient Expired"
      or InpatientEncounter.dischargeDisposition in "Left Against Medical Advice") */
  SELECT *
  FROM
    (SELECT *
     FROM {{ ref('SafeUseofOpioidsConcurrentPrescribingInpatient_Encounters_with_an_Opioid_or_Benzodiazepine_at_Discharge') }}) AS InpatientEncounter WHERE ((((((EXISTS
                                                                                                                                                                    (SELECT *
                                                                                                                                                                     FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1111.180', 'qdm', 'diagnosis', 'v56', 'code') }}) AS Cancer
                                                                                                                                                                     WHERE (Cancer.prevalencePeriod.start<=InpatientEncounter.relevantPeriod.endANDCancer.prevalencePeriod.end>=InpatientEncounter.relevantPeriod.start))
                                                                                                                                                                  OR EXISTS
                                                                                                                                                                    (SELECT *
                                                                                                                                                                     FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1111.175', 'qdm', 'diagnosis', 'v56', 'code') }}) AS SickleCellDisease
                                                                                                                                                                     WHERE (SickleCellDisease.prevalencePeriod.start<=InpatientEncounter.relevantPeriod.endANDSickleCellDisease.prevalencePeriod.end>=InpatientEncounter.relevantPeriod.start)))
                                                                                                                                                                 OR EXISTS
                                                                                                                                                                   (SELECT *
                                                                                                                                                                    FROM
                                                                                                                                                                      (/* warning -- speculative coercion follows -- SIMPLE to QUERY/TABLE */SELECT col.*
                                                                                                                                                                       FROM (explode(InpatientEncounter.diagnoses))) AS Diagnosis
                                                                                                                                                                    WHERE EXISTS
                                                                                                                                                                        (SELECT codes.code AS code
                                                                                                                                                                         FROM ({{ transpilerValuesetCodes('2.16.840.1.113762.1.4.1111.180', none) }})
                                                                                                                                                                         WHERE code = Diagnosis.code)))
                                                                                                                                                                OR EXISTS
                                                                                                                                                                  (SELECT *
                                                                                                                                                                   FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1111.171', 'qdm', 'diagnosis', 'v56', 'code') }}) AS OUD
                                                                                                                                                                   WHERE (OUD.prevalencePeriod.low < InpatientEncounter.relevantPeriod.high)))
                                                                                                                                                               OR EXISTS
                                                                                                                                                                 (SELECT *
                                                                                                                                                                  FROM
                                                                                                                                                                    (SELECT *
                                                                                                                                                                     FROM {{ ref('SafeUseofOpioidsConcurrentPrescribingTreatment_for_Opioid_Use_Disorders') }}) AS OUDTreatment
                                                                                                                                                                  WHERE (coalesce(IF(NOT OUDTreatment.relevantDatetime IS NULL, struct(OUDTreatment.relevantDatetime AS low, OUDTreatment.relevantDatetime AS high), IF(NOT OUDTreatment.relevantPeriod IS NULL, OUDTreatment.relevantPeriod, NULL)).low, OUDTreatment.authorDatetime) BETWEEN InpatientEncounter.relevantPeriod.low AND InpatientEncounter.relevantPeriod.high)))
                                                                                                                                                              OR EXISTS
                                                                                                                                                                (SELECT *
                                                                                                                                                                 EXCEPT (_aliasRef_InpatientEncounter)
                                                                                                                                                                 FROM
                                                                                                                                                                   (SELECT *
                                                                                                                                                                    FROM
                                                                                                                                                                      (SELECT *
                                                                                                                                                                       FROM {{ ref('SafeUseofOpioidsConcurrentPrescribingIntervention_Palliative_or_Hospice_Care') }}) AS PalliativeOrHospiceCare
                                                                                                                                                                    WHERE (coalesce(IF(NOT PalliativeOrHospiceCare.relevantDatetime IS NULL, struct(PalliativeOrHospiceCare.relevantDatetime AS low, PalliativeOrHospiceCare.relevantDatetime AS high), IF(NOT PalliativeOrHospiceCare.relevantPeriod IS NULL, PalliativeOrHospiceCare.relevantPeriod, NULL)).low, PalliativeOrHospiceCare.authorDatetime) BETWEEN
                                                                                                                                                                             (WITH _Visit AS
                                                                                                                                                                                (SELECT _aliasRef_InpatientEncounter.*
                                                                                                                                                                                 FROM
                                                                                                                                                                                   (SELECT _aliasRef_InpatientEncounter)),
                                                                                                                                                                                   _let_ObsVisit_cte AS
                                                                                                                                                                                (SELECT Visit.*,

                                                                                                                                                                                   (SELECT last_value(struct(*)) AS _val
                                                                                                                                                                                    FROM
                                                                                                                                                                                      (SELECT *
                                                                                                                                                                                       FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1111.143', 'qdm', 'encounter_performed', 'v56', 'code') }}) AS LastObs
                                                                                                                                                                                       WHERE ((LastObs.relevantPeriod.high BETWEEN struct((Visit.relevantPeriod.low - INTERVAL 1 HOUR) AS low, Visit.relevantPeriod.low AS high).low AND struct((Visit.relevantPeriod.low - INTERVAL 1 HOUR) AS low, Visit.relevantPeriod.low AS high).high)
                                                                                                                                                                                              AND NOT Visit.relevantPeriod.low IS NULL)
                                                                                                                                                                                       ORDER BY relevantPeriod.high ASC) AS lastSource
                                                                                                                                                                                    WHERE lastSource.patientId = Visit.patientId) AS _let_ObsVisit
                                                                                                                                                                                 FROM _Visit AS Visit),
                                                                                                                                                                                   _let_VisitStart_cte AS
                                                                                                                                                                                (SELECT Visit.*,

                                                                                                                                                                                   (SELECT coalesce(_let_ObsVisit.relevantPeriod.low, Visit.relevantPeriod.low) _val) AS _let_VisitStart
                                                                                                                                                                                 FROM _let_ObsVisit_cte AS Visit),
                                                                                                                                                                                   _let_EDVisit_cte AS
                                                                                                                                                                                (SELECT Visit.*,

                                                                                                                                                                                   (SELECT last_value(struct(*)) AS _val
                                                                                                                                                                                    FROM
                                                                                                                                                                                      (SELECT *
                                                                                                                                                                                       FROM ({{ transpilerRetrieve('2.16.840.1.113883.3.117.1.7.1.292', 'qdm', 'encounter_performed', 'v56', 'code') }}) AS LastED
                                                                                                                                                                                       WHERE ((LastED.relevantPeriod.high BETWEEN struct((_let_VisitStart - INTERVAL 1 HOUR) AS low, _let_VisitStart AS high).low AND struct((_let_VisitStart - INTERVAL 1 HOUR) AS low, _let_VisitStart AS high).high)
                                                                                                                                                                                              AND NOT _let_VisitStart IS NULL)
                                                                                                                                                                                       ORDER BY relevantPeriod.high ASC) AS lastSource
                                                                                                                                                                                    WHERE lastSource.patientId = Visit.patientId) AS _let_EDVisit
                                                                                                                                                                                 FROM _let_VisitStart_cte AS Visit) SELECT (struct(coalesce(_let_EDVisit.relevantPeriod.low, _let_VisitStart) AS low, Visit.relevantPeriod.high AS high))
                                                                                                                                                                              FROM _let_EDVisit_cte AS Visit).low AND
                                                                                                                                                                             (WITH _Visit AS
                                                                                                                                                                                (SELECT _aliasRef_InpatientEncounter.*
                                                                                                                                                                                 FROM
                                                                                                                                                                                   (SELECT _aliasRef_InpatientEncounter)),
                                                                                                                                                                                   _let_ObsVisit_cte AS
                                                                                                                                                                                (SELECT Visit.*,

                                                                                                                                                                                   (SELECT last_value(struct(*)) AS _val
                                                                                                                                                                                    FROM
                                                                                                                                                                                      (SELECT *
                                                                                                                                                                                       FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1111.143', 'qdm', 'encounter_performed', 'v56', 'code') }}) AS LastObs
                                                                                                                                                                                       WHERE ((LastObs.relevantPeriod.high BETWEEN struct((Visit.relevantPeriod.low - INTERVAL 1 HOUR) AS low, Visit.relevantPeriod.low AS high).low AND struct((Visit.relevantPeriod.low - INTERVAL 1 HOUR) AS low, Visit.relevantPeriod.low AS high).high)
                                                                                                                                                                                              AND NOT Visit.relevantPeriod.low IS NULL)
                                                                                                                                                                                       ORDER BY relevantPeriod.high ASC) AS lastSource
                                                                                                                                                                                    WHERE lastSource.patientId = Visit.patientId) AS _let_ObsVisit
                                                                                                                                                                                 FROM _Visit AS Visit),
                                                                                                                                                                                   _let_VisitStart_cte AS
                                                                                                                                                                                (SELECT Visit.*,

                                                                                                                                                                                   (SELECT coalesce(_let_ObsVisit.relevantPeriod.low, Visit.relevantPeriod.low) _val) AS _let_VisitStart
                                                                                                                                                                                 FROM _let_ObsVisit_cte AS Visit),
                                                                                                                                                                                   _let_EDVisit_cte AS
                                                                                                                                                                                (SELECT Visit.*,

                                                                                                                                                                                   (SELECT last_value(struct(*)) AS _val
                                                                                                                                                                                    FROM
                                                                                                                                                                                      (SELECT *
                                                                                                                                                                                       FROM ({{ transpilerRetrieve('2.16.840.1.113883.3.117.1.7.1.292', 'qdm', 'encounter_performed', 'v56', 'code') }}) AS LastED
                                                                                                                                                                                       WHERE ((LastED.relevantPeriod.high BETWEEN struct((_let_VisitStart - INTERVAL 1 HOUR) AS low, _let_VisitStart AS high).low AND struct((_let_VisitStart - INTERVAL 1 HOUR) AS low, _let_VisitStart AS high).high)
                                                                                                                                                                                              AND NOT _let_VisitStart IS NULL)
                                                                                                                                                                                       ORDER BY relevantPeriod.high ASC) AS lastSource
                                                                                                                                                                                    WHERE lastSource.patientId = Visit.patientId) AS _let_EDVisit
                                                                                                                                                                                 FROM _let_VisitStart_cte AS Visit) SELECT (struct(coalesce(_let_EDVisit.relevantPeriod.low, _let_VisitStart) AS low, Visit.relevantPeriod.high AS high))
                                                                                                                                                                              FROM _let_EDVisit_cte AS Visit).high) )))
                                                                                                                                                             OR (((EXISTS
                                                                                                                                                                     (SELECT codes.code AS code
                                                                                                                                                                      FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.87', none) }})
                                                                                                                                                                      WHERE code = InpatientEncounter.dischargeDisposition)
                                                                                                                                                                   OR EXISTS
                                                                                                                                                                     (SELECT codes.code AS code
                                                                                                                                                                      FROM ({{ transpilerValuesetCodes('2.16.840.1.113762.1.4.1116.365', none) }})
                                                                                                                                                                      WHERE code = InpatientEncounter.dischargeDisposition))
                                                                                                                                                                  OR EXISTS
                                                                                                                                                                    (SELECT codes.code AS code
                                                                                                                                                                     FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.309', none) }})
                                                                                                                                                                     WHERE code = InpatientEncounter.dischargeDisposition))
                                                                                                                                                                 OR EXISTS
                                                                                                                                                                   (SELECT codes.code AS code
                                                                                                                                                                    FROM ({{ transpilerValuesetCodes('2.16.840.1.113883.3.117.1.7.1.308', none) }})
                                                                                                                                                                    WHERE code = InpatientEncounter.dischargeDisposition)))