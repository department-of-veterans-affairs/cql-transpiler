/*
Defines the minimum CQL language features the engine must support for QUICK based measures.
*/
library QuickEngineConformity version '1.0'

using QUICK

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

// TODO: define "Date Filtered Retrieve":
// TODO: define "Date and ValueSet Filtered Retrieve":
// TODO: define and prove transitive filters

define "Date Filter":
    "Unfiltered Retrieve" E
    // TODO: where (E.period as Interval<DateTime>) ends during MeasurementPeriod

define "Code Filter":
    "Unfiltered Retrieve" E
    where E.type.codes in "Ambulatory/ED Visit"

// Confirm required Date Operations
define "High/Low Property Access of Period":
    "Unfiltered Retrieve" E
    return Tuple { highClosed: E.period.highClosed, high: E.period.high, lowClosed: E.period.lowClosed, low: E.period.low }

define "Explicit Cast of Period to Interval":
    "Unfiltered Retrieve" E
    where E.period as Interval<DateTime> ends during MeasurementPeriod
    return Tuple {
        convertPeriodToInterval: convert E.period to Interval<DateTime>,
        castPeriodToInterval: E.period as Interval<DateTime>
    }

define "Timing Phrases of Period":
    "Unfiltered Retrieve" E
    return Tuple {
        testStart: start of (E.period as Interval<DateTime>),       // NOTE: Only explicit cast works
        testEnd: end of (E.period as Interval<DateTime>)            // NOTE: Only explicit cast works
    }

define "GE LT of Period":
    "Unfiltered Retrieve" E
    where E.period.high >= @2010-01-01 and E.period.high < @2022-01-01

define "GT LE of Period":
    "Unfiltered Retrieve" E
    where E.period.high > @2010-12-31 and E.period.high <= @2022-12-31

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
    return { ageAtTest: AgeInYearsAt(E.period.high) }

