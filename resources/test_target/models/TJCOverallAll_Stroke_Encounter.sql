SELECT * FROM (SELECT * FROM (/*<Unsupported Retrieve with arguments <modeltype: urn:healthit-gov:qdm:v5_6, templateId: PositiveEncounterPerformed>*/) AS NonElectiveEncounter WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN @Measurement_Period.low AND @Measurement_Period.high)) AS NonElectiveEncounter WHERE EXISTS (SELECT * FROM (NonElectiveEncounter.diagnoses) AS Diagnosis WHERE ((Diagnosis.rank = 1) AND (/*<Unsupported InValueSet>*/ OR /*<Unsupported InValueSet>*/)))