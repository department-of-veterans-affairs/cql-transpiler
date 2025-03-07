library SafeUseofOpioidsConcurrentPrescribing version '7.0.000'

using QDM version '5.6'

include MATGlobalCommonFunctionsQDM version '8.0.000' called Global

valueset "Cancer Related Pain": 'urn:oid:2.16.840.1.113762.1.4.1111.180'
valueset "Discharge To Acute Care Facility": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.87'
valueset "Ethnicity": 'urn:oid:2.16.840.1.114222.4.11.837'
valueset "Hospice Care Referral or Admission": 'urn:oid:2.16.840.1.113762.1.4.1116.365'
valueset "Left Against Medical Advice": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.308'
valueset "Medications for Opioid Use Disorder (MOUD)": 'urn:oid:2.16.840.1.113762.1.4.1046.269'
valueset "ONC Administrative Sex": 'urn:oid:2.16.840.1.113762.1.4.1'
valueset "Opioid Medication Assisted Treatment (MAT)": 'urn:oid:2.16.840.1.113762.1.4.1111.177'
valueset "Opioid Use Disorder": 'urn:oid:2.16.840.1.113762.1.4.1111.171'
valueset "Palliative or Hospice Care": 'urn:oid:2.16.840.1.113883.3.600.1.1579'
valueset "Patient Expired": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.309'
valueset "Payer Type": 'urn:oid:2.16.840.1.114222.4.11.3591'
valueset "Race": 'urn:oid:2.16.840.1.114222.4.11.836'
valueset "Schedule II, III and IV Opioid Medications": 'urn:oid:2.16.840.1.113762.1.4.1046.241'
valueset "Schedule IV Benzodiazepines": 'urn:oid:2.16.840.1.113762.1.4.1125.1'
valueset "Sickle Cell Disease with and without Crisis": 'urn:oid:2.16.840.1.113762.1.4.1111.175'

parameter "Measurement Period" Interval<DateTime>

context Patient

define "Denominator":
  "Initial Population"

define "Inpatient Encounter with Age Greater than or Equal to 18":
  Global."Inpatient Encounter" InpatientHospitalEncounter
    where AgeInYearsAt(date from start of InpatientHospitalEncounter.relevantPeriod) >= 18

define "Intervention Palliative or Hospice Care":
  ["Intervention, Order": "Palliative or Hospice Care"]
    union ["Intervention, Performed": "Palliative or Hospice Care"]

define "Initial Population":
  "Inpatient Encounters with an Opioid or Benzodiazepine at Discharge"

define "Treatment for Opioid Use Disorders":
  ( ["Medication, Active": "Medications for Opioid Use Disorder (MOUD)"]
    union ["Medication, Order": "Medications for Opioid Use Disorder (MOUD)"] ) MedicationTreatment
    with ["Intervention, Performed": "Opioid Medication Assisted Treatment (MAT)"] MAT
      such that Coalesce(start of Global."NormalizeInterval"(MedicationTreatment.relevantDatetime, MedicationTreatment.relevantPeriod), MedicationTreatment.authorDatetime) during day of Global."NormalizeInterval" ( MAT.relevantDatetime, MAT.relevantPeriod )
        and Coalesce(start of Global."NormalizeInterval"(MedicationTreatment.relevantDatetime, MedicationTreatment.relevantPeriod), MedicationTreatment.authorDatetime) during day of "Measurement Period"

define "SDE Ethnicity":
  ["Patient Characteristic Ethnicity": "Ethnicity"]

define "SDE Race":
  ["Patient Characteristic Race": "Race"]

define "SDE Sex":
  ["Patient Characteristic Sex": "ONC Administrative Sex"]

define "SDE Payer":
  ["Patient Characteristic Payer": "Payer Type"]

define "Denominator Exclusion":
  /*Excludes encounters of patients with cancer pain or who are receiving palliative or hospice care at the time of the encounter or who are receiving medication for opioid use disorder, have sickle cell disease, or who are discharged to another inpatient care facility or discharged against medical advice, or expire during the inpatient stay*/
  
  "Inpatient Encounters with an Opioid or Benzodiazepine at Discharge" InpatientEncounter
    where exists ( ["Diagnosis": "Cancer Related Pain"] Cancer
        where Cancer.prevalencePeriod overlaps day of InpatientEncounter.relevantPeriod
    )
      or exists ( ["Diagnosis": "Sickle Cell Disease with and without Crisis"] SickleCellDisease
          where SickleCellDisease.prevalencePeriod overlaps InpatientEncounter.relevantPeriod
      )
      or exists ( InpatientEncounter.diagnoses Diagnosis
          where Diagnosis.code in "Cancer Related Pain"
      )
      or exists ( ["Diagnosis": "Opioid Use Disorder"] OUD
          where start of OUD.prevalencePeriod before day of end of InpatientEncounter.relevantPeriod
      )
      or exists ( "Treatment for Opioid Use Disorders" OUDTreatment
          where Coalesce(start of Global."NormalizeInterval"(OUDTreatment.relevantDatetime, OUDTreatment.relevantPeriod), OUDTreatment.authorDatetime) during day of InpatientEncounter.relevantPeriod
      )
      or exists ( "Intervention Palliative or Hospice Care" PalliativeOrHospiceCare
          where Coalesce(start of Global."NormalizeInterval"(PalliativeOrHospiceCare.relevantDatetime, PalliativeOrHospiceCare.relevantPeriod), PalliativeOrHospiceCare.authorDatetime) during Global."HospitalizationWithObservation" ( InpatientEncounter )
      )
      or ( InpatientEncounter.dischargeDisposition in "Discharge To Acute Care Facility"
          or InpatientEncounter.dischargeDisposition in "Hospice Care Referral or Admission"
          or InpatientEncounter.dischargeDisposition in "Patient Expired"
          or InpatientEncounter.dischargeDisposition in "Left Against Medical Advice"
      )

define "Inpatient Encounters with an Opioid or Benzodiazepine at Discharge":
  /*Captures encounters of patients with an opioid(s), benzodiazepine, or a combination of these medications at discharge*/
  
  "Inpatient Encounter with Age Greater than or Equal to 18" InpatientEncounter
    with ( ["Medication, Discharge": "Schedule II, III and IV Opioid Medications"]
      union ["Medication, Discharge": "Schedule IV Benzodiazepines"] ) OpioidOrBenzodiazepineDischargeMedication
      such that OpioidOrBenzodiazepineDischargeMedication.authorDatetime during day of InpatientEncounter.relevantPeriod

define "Numerator":
  /*Encounters of patients prescribed two or more opioids or an opioid and benzodiazepine at discharge.
  */
  
  ( "Inpatient Encounters with an Opioid or Benzodiazepine at Discharge" InpatientEncounter
      where ( Count(["Medication, Discharge": "Schedule II, III and IV Opioid Medications"] Opioids
            where Opioids.authorDatetime during day of InpatientEncounter.relevantPeriod
            return distinct Opioids.code
        ) >= 2
      )
        or exists ( ["Medication, Discharge": "Schedule II, III and IV Opioid Medications"] OpioidsDischarge
            where OpioidsDischarge.authorDatetime during day of InpatientEncounter.relevantPeriod
              and exists ["Medication, Discharge": "Schedule IV Benzodiazepines"] BenzodiazepinesDischarge
                where BenzodiazepinesDischarge.authorDatetime during day of InpatientEncounter.relevantPeriod
        )
  )