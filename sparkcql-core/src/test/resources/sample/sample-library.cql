library SAMPLE_LIBRARY version '1.0'

using SAMPLE

valueset "Sample Valueset A": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'

parameter "Measurement Period" Interval<DateTime>

context Patient

define "Sample Definition A":
  ["Entity": "Sample Valueset A"] E

// define "Sample Definition B":
// ["SampleDomain": "Sample Valueset A"] E
//   where E.name = "sample name 2"