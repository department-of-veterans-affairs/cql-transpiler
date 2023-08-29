library MOCK_LIBRARY version '1.0'

using MOCK

valueset "Mock Valueset A": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'

parameter "Measurement Period" Interval<DateTime>

context Patient

define "Mock Definition A":
  ["MockEntity": "Mock Valueset A"] E