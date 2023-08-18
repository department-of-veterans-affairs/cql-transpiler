library SIMPLE_QUICK_RETRIEVE version '1.0'

using QUICK

valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'

context Patient

define "All Encounters": ["Encounter"] E

define "Encounters After 2013": ["Encounter"] E where E.period ends after @2013-12-27T14:30

define "ED Encounters": ["Encounter": "Emergency Department Visit"] E

define "ED Encounters After 2013": ["Encounter": "Emergency Department Visit"] E where E.period ends after @2013-12-27T14:30

define "All Conditions": ["Condition"] C