library DischargedonAntithromboticTherapy version '11.0.000'

using QDM version '5.6'

include MATGlobalCommonFunctions version '7.0.000' called Global
include TJCOverall version '6.0.000' called TJC

valueset "Antithrombotic Therapy": 'urn:oid:2.16.840.1.113762.1.4.1110.62'
valueset "Ethnicity": 'urn:oid:2.16.840.1.114222.4.11.837'
valueset "Medical Reason": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.473'
valueset "ONC Administrative Sex": 'urn:oid:2.16.840.1.113762.1.4.1'
valueset "Patient Refusal": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.93'
valueset "Payer": 'urn:oid:2.16.840.1.114222.4.11.3591'
valueset "Pharmacological Contraindications For Antithrombotic Therapy": 'urn:oid:2.16.840.1.113762.1.4.1110.52'
valueset "Race": 'urn:oid:2.16.840.1.114222.4.11.836'

context Patient

define "SDE Ethnicity":
  ["Patient Characteristic Ethnicity": "Ethnicity"]

define "SDE Payer":
  ["Patient Characteristic Payer": "Payer"]

define "SDE Race":
  ["Patient Characteristic Race": "Race"]

define "SDE Sex":
  ["Patient Characteristic Sex": "ONC Administrative Sex"]

define "Antithrombotic Therapy at Discharge":
  ["Medication, Discharge": "Antithrombotic Therapy"]

define "Denominator":
  TJC."Ischemic Stroke Encounter"

define "Numerator":
  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
    with "Antithrombotic Therapy at Discharge" DischargeAntithrombotic
      such that DischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod

define "Initial Population":
  TJC."Encounter with Principal Diagnosis and Age"

define "Denominator Exceptions":
  "Encounter with No Antithrombotic At Discharge"
    union "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge"

define "Denominator Exclusions":
  TJC."Ischemic Stroke Encounters with Discharge Disposition"
    union TJC."Encounter with Comfort Measures during Hospitalization"

define "Encounter with No Antithrombotic At Discharge":
  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
    with "Reason for Not Giving Antithrombotic at Discharge" NoDischargeAntithrombotic
      such that NoDischargeAntithrombotic.authorDatetime during IschemicStrokeEncounter.relevantPeriod

define "Encounter with Pharmacological Contraindications for Antithrombotic Therapy at Discharge":
  TJC."Ischemic Stroke Encounter" IschemicStrokeEncounter
    with ["Medication, Discharge": "Pharmacological Contraindications For Antithrombotic Therapy"] Pharmacological
      such that Pharmacological.authorDatetime during IschemicStrokeEncounter.relevantPeriod

define "Reason for Not Giving Antithrombotic at Discharge":
  ["Medication, Not Discharged": "Antithrombotic Therapy"] NoAntithromboticDischarge
    where NoAntithromboticDischarge.negationRationale in "Medical Reason"
      or NoAntithromboticDischarge.negationRationale in "Patient Refusal"