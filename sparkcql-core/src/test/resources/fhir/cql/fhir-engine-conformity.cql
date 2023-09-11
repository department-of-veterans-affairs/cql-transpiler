/*
Defines the minimum CQL language features the engine must support for FHIR based measures.
*/
library FhirEngineConformity version '1.0'

using FHIR version '4.0.1'

include FHIRHelpers version '4.1.000'

valueset "Ambulatory/ED Visit": 'urn:oid:2.16.840.1.113883.3.464.1003.101.12.1061'

parameter MeasurementPeriod default Interval[
  @2013-01-01T00:00:00.0,
  @2014-01-01T00:00:00.0
)

context Patient

// Confirm required retrieval predicates are supported
define "Unfiltered Retrieve":
    [Encounter]

define "ValueSet Filtered Retrieve":
    [Encounter: "Ambulatory/ED Visit"] E

// TODO: define "Date and ValueSet Filtered Retrieve":
// TODO: define and prove transitive filters

define "Date Filter":
    "Unfiltered Retrieve" E
    where E.period ends during MeasurementPeriod

define "Code Filter":
    "Unfiltered Retrieve" E
    where E.type in "Ambulatory/ED Visit"

// Confirm required Date Operations
define "High/Low Property Access of Period":
    "Unfiltered Retrieve" E
    return Tuple { high: E.period.start, low: E.period.end }

define "Timing Phrases of Period":
    "Unfiltered Retrieve" E
    return Tuple {
        testStart: start of E.period,
        testEnd: end of E.period
    }

define "GE LT of Period":
    "Unfiltered Retrieve" E
    where E.period.start >= @2010-01-01 and E.period.start < @2022-01-01

define "GT LE of Period":
    "Unfiltered Retrieve" E
    where E.period.start > @2010-12-31 and E.period.start <= @2022-12-31

// Confirm required DataTypes are supported
define "Encounter Retrieve":
    [Encounter]

define "Condition Retrieve":
    [Condition]

define "MedicationAdministration Retrieve":
    [MedicationAdministration]

// Age Calculations
define "Age In Years":
    "Encounter Retrieve" E
    return { ageAtTest: AgeInYearsAt(E.period.start), ageTest: AgeInYears() }