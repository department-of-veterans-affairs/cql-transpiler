library Retrievals version '1.0'

using QDM version '5.6'
include MATGlobalCommonFunctions version '7.0.000' called Global
valueset "Nonelective Inpatient Encounter": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424'
parameter "Measurement Period" Interval<DateTime>
define "Non Elective Inpatient Encounter":
    ["Encounter, Performed": "Nonelective Inpatient Encounter"] NonElectiveEncounter
        where Global."LengthInDays" (NonElectiveEncounter.relevantPeriod) <= 120
        and NonElectiveEncounter.relevantPeriod ends during day of "Measurement Period"