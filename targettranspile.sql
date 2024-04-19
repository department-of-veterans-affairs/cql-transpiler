-- Current:

/*
	// TJCOverall lines [58:1-61:85]
	define "Non Elective Inpatient Encounter":
	  ["Encounter, Performed": "Nonelective Inpatient Encounter"] NonElectiveEncounter
	    where Global."LengthInDays" ( NonElectiveEncounter.relevantPeriod ) <= 120
	      and NonElectiveEncounter.relevantPeriod ends during day of "Measurement Period"
*/

SELECT * FROM (SELECT * FROM urn:oid:2.16.840.1.113883.3.117.1.7.1.424 AS NonElectiveEncounter) WHERE ((DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN SELECT low FROM @Measurement_Period AND SELECT high FROM @Measurement_Period)


-- Target:
SELECT * FROM (SELECT * FROM feature_transpiler_testing_fhirlake.specification__valueset AS NonElectiveEncounter) WHERE (DATEDIFF(NonElectiveEncounter.relevantPeriod.high, NonElectiveEncounter.relevantPeriod.low) <= 120) AND NonElectiveEncounter.relevantPeriod.high BETWEEN '2000-01-01' AND '2030-01-01'