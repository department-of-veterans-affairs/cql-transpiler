library SimpleStandalone version '1.0'

//using FHIR version '4.0.0'
using QUICK

codesystem "SNOMED": 'http://snomed.info/sct'
code "Acute Pharyngitis Code": '363746003' from "SNOMED" display 'Acute pharyngitis (disorder)'
code "Type Code": '12345678' from "SNOMED"
valueset "Ambulatory/ED Visit": 'urn:oid:2.16.840.1.113883.3.464.1003.101.12.1061'

context Patient

define "Encounter A":
    [Encounter]

define "Encounter B":
    [Encounter: "Ambulatory/ED Visit"]

define "Encounter C":
    [Encounter: type = "Type Code"]

define "Encounter Combined":
    "Encounter A" A
    with "Encounter B" B
        such that A.period during B.period

define "MyTupleLiteralList":
    {
        Tuple {
            Name: 'Patrick',
            DOB: @2014-01-01,
            Address: Tuple { Line1: '41 Spinning Ave', City: 'Dayton', State: 'OH' },
            Phones: { Tuple { Number: '202-413-1234', Use: 'Home' } }
        },
        {
            Name: 'Beth',
            DOB: @2014-01-01,
            Address: Tuple { Line1: '123 Main Ave', City: 'Anytown', State: 'GA' },
            Phones: { Tuple { Number: '202-413-1234', Use: 'Home' } }
        }
    }