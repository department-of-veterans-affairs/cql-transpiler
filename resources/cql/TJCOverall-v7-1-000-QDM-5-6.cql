library TJCOverall version '7.1.000'

using QDM version '5.6'

include MATGlobalCommonFunctions version '7.0.000' called Global

valueset "Comfort Measures": 'urn:oid:1.3.6.1.4.1.33895.1.3.0.45' 
valueset "Discharge To Acute Care Facility": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.87' 
valueset "Discharged to Health Care Facility for Hospice Care": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.207' 
valueset "Discharged to Home for Hospice Care": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.209' 
valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292' 
valueset "Hemorrhagic Stroke": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.212' 
valueset "Ischemic Stroke": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.247' 
valueset "Left Against Medical Advice": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.308' 
valueset "Nonelective Inpatient Encounter": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424' 
valueset "Observation Services": 'urn:oid:2.16.840.1.113762.1.4.1111.143' 
valueset "Patient Expired": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.309' 

parameter "Measurement Period" Interval<DateTime>

context Patient

define "Intervention Comfort Measures":
  ["Intervention, Order": "Comfort Measures"]
    union ["Intervention, Performed": "Comfort Measures"]

define "Ischemic Stroke Encounters with Discharge Disposition":
  ( ( "Ischemic Stroke Encounter" IschemicStrokeEncounter
        where IschemicStrokeEncounter.dischargeDisposition in "Discharge To Acute Care Facility"
          or IschemicStrokeEncounter.dischargeDisposition in "Left Against Medical Advice"
          or IschemicStrokeEncounter.dischargeDisposition in "Patient Expired"
          or IschemicStrokeEncounter.dischargeDisposition in "Discharged to Home for Hospice Care"
          or IschemicStrokeEncounter.dischargeDisposition in "Discharged to Health Care Facility for Hospice Care"
    )
  )

define "Ischemic Stroke Encounter":
  "Encounter with Principal Diagnosis and Age" EncounterWithAge
    where exists ( EncounterWithAge.diagnoses Diagnosis
        where Diagnosis.code in "Ischemic Stroke"
          and Diagnosis.rank = 1
    )

define "All Stroke Encounter":
  "Non Elective Inpatient Encounter" NonElectiveEncounter
    where exists ( NonElectiveEncounter.diagnoses Diagnosis
        where Diagnosis.rank = 1
          and ( Diagnosis.code in "Hemorrhagic Stroke"
              or Diagnosis.code in "Ischemic Stroke"
          )
    )

define "Encounter with Comfort Measures during Hospitalization":
  "Ischemic Stroke Encounter" IschemicStrokeEncounter
    with "Intervention Comfort Measures" ComfortMeasure
      such that Coalesce(start of Global."NormalizeInterval"(ComfortMeasure.relevantDatetime, ComfortMeasure.relevantPeriod), ComfortMeasure.authorDatetime)during Global."HospitalizationWithObservation" ( IschemicStrokeEncounter )

define "Non Elective Inpatient Encounter":
  ["Encounter, Performed": "Nonelective Inpatient Encounter"] NonElectiveEncounter
    where Global."LengthInDays" ( NonElectiveEncounter.relevantPeriod ) <= 120
      and NonElectiveEncounter.relevantPeriod ends during day of "Measurement Period"

define "Encounter with Principal Diagnosis and Age":
  "All Stroke Encounter" AllStrokeEncounter
    where AgeInYearsAt(date from start of AllStrokeEncounter.relevantPeriod)>= 18

define function "HospitalizationWithObservationLengthofStay"(Encounter "Encounter, Performed" ):
  Global."LengthInDays" ( Global."HospitalizationWithObservation" ( Encounter ) )

define function "TruncateTime"(Value DateTime ):
  DateTime(year from Value, month from Value, day from Value, 0, 0, 0, 0, timezoneoffset from Value)

define function "CalendarDayOfOrDayAfter"(StartValue DateTime ):
  Interval["TruncateTime"(StartValue), "TruncateTime"(StartValue + 2 days))

