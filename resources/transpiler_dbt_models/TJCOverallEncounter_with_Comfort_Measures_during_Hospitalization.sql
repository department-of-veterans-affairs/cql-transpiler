/*
define "Encounter with Comfort Measures during Hospitalization":
  "Ischemic Stroke Encounter" IschemicStrokeEncounter
    with "Intervention Comfort Measures" ComfortMeasure
      such that Coalesce(start of Global."NormalizeInterval"(ComfortMeasure.relevantDatetime, ComfortMeasure.relevantPeriod), ComfortMeasure.authorDatetime)during Global."HospitalizationWithObservation" ( IschemicStrokeEncounter )
*/
SELECT *
EXCEPT (_aliasRef_IschemicStrokeEncounter)
FROM
  (WITH _IschemicStrokeEncounter AS
     (SELECT _source.*,
             struct(*) AS _aliasRef_IschemicStrokeEncounter
      FROM
        (SELECT *
         FROM {{ ref('TJCOverallIschemic_Stroke_Encounter') }}) AS _source),
        ComfortMeasure AS
     (SELECT *
      FROM {{ ref('TJCOverallIntervention_Comfort_Measures') }}) SELECT *
   FROM
     (SELECT IschemicStrokeEncounter.*
      FROM _IschemicStrokeEncounter AS IschemicStrokeEncounter
      LEFT JOIN ComfortMeasure USING (patientId)
      WHERE (coalesce(IF(NOT ComfortMeasure.relevantDatetime IS NULL, struct(ComfortMeasure.relevantDatetime AS low, ComfortMeasure.relevantDatetime AS high), IF(NOT ComfortMeasure.relevantPeriod IS NULL, ComfortMeasure.relevantPeriod, NULL)).low, ComfortMeasure.authorDatetime) BETWEEN
               (WITH _Visit AS
                  (SELECT _aliasRef_IschemicStrokeEncounter.*
                   FROM
                     (SELECT _aliasRef_IschemicStrokeEncounter)),
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
                  (SELECT _aliasRef_IschemicStrokeEncounter.*
                   FROM
                     (SELECT _aliasRef_IschemicStrokeEncounter)),
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
                FROM _let_EDVisit_cte AS Visit).high)))