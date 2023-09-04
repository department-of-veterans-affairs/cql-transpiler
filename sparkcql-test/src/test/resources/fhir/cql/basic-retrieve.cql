library BasicRetrieve version '1.0'

using QUICK

codesystem "SNOMED": 'http://snomed.info/sct'
code "Acute Pharyngitis Code": '363746003' from "SNOMED" display 'Acute pharyngitis (disorder)'
code "Type Code": '12345678' from "SNOMED"
valueset "Ambulatory/ED Visit": 'urn:oid:2.16.840.1.113883.3.464.1003.101.12.1061'

parameter MeasurementPeriod default Interval[
  @2013-01-01T00:00:00.0,
  @2014-01-01T00:00:00.0
)

context Patient

define "Encounter A":
    [Encounter] E
    // return Tuple { id: E.identifier, lengthOfStay: convert E.period to Interval<DateTime> }  works
    // return Tuple { id: E.identifier, lengthOfStay: start of E.period }
    // return Tuple { id: E.identifier, lengthOfStay: E.period }
    // where E.period starts before @2014-01-25
    // where (E.period as Interval<DateTime>) ends during MeasurementPeriod   works
    where E.period ends during MeasurementPeriod


//
//define "Condition A":
//    [Condition] E
//      where AgeInYearsAt(E.abatementDate) >= 18
//        and E.abatementDate > @2014-01-25
//        and E.abatementDate > end of E.onsetPeriod


//define "Encounter B":
//    [Encounter: "Ambulatory/ED Visit"] E
    //return Tuple { id: E.identifier, lengthOfStay: end of E.period }
    // where end of E.period > @2014-01-25


//define "foo": 42

//define "Encounter Combined":
//    "Encounter A" A
//    with "Encounter B" B
//        such that start of A.period during B.period
// where duration in days of E.period >= 120
    // where AgeInYearsAt(date from(start of E.period))>= 18
    //where start of E.period > @2014-01-25
