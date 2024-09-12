library Retrievals version '1.0'

using QUICK

valueset "Ambulatory/ED Visit": 'urn:oid:2.16.840.1.113883.3.464.1003.101.12.1061'

context Patient

define "Encounter A":
    [Encounter] E

define "Encounter B":
    [Encounter: "Ambulatory/ED Visit"] E