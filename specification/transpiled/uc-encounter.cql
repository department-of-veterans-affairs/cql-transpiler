library UC_Encounter version '1.0'

using QDM version '5.6'

valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'
valueset "Encounter Inpatient": 'urn:oid:2.16.840.1.113883.3.666.5.307'
valueset "Ischemic Stroke": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.247'

parameter "Measurement Period" Interval<DateTime>

context Patient

define "ED Encounter":
  ["Encounter, Performed": "Emergency Department Visit"]

define "Inpatient Encounter":
  ["Encounter, Performed": "Encounter Inpatient"] EncounterInpatient
    where "LengthInDays"(EncounterInpatient.relevantPeriod)<= 120
      and EncounterInpatient.relevantPeriod ends during day of "Measurement Period"

define "Ischemic Stroke Encounter":
  "Encounter with Principal Diagnosis and Age" EncounterWithAge
    where exists ( EncounterWithAge.diagnoses Diagnosis
        where Diagnosis.code in "Ischemic Stroke"
          and Diagnosis.rank = 1
    )

define function "LengthInDays"(Value 	Interval<DateTime> ):
  difference in days between start of Value and end of Value