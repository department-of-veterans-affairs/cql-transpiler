library ED version '1.0'

using QUICK

valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292' 

parameter "Measurement Period" Interval<DateTime>

context Patient

define "ED Encounter":
  ["Encounter, Performed": "Emergency Department Visit"] ED
    where ED.relevantPeriod ends during day of "Measurement Period"