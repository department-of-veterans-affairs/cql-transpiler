/*
define "Inpatient Encounters with an Opioid or Benzodiazepine at Discharge":
  /*Captures encounters of patients with an opioid(s), benzodiazepine, or a combination of these medications at discharge*/ "Inpatient Encounter with Age Greater than or Equal to 18" InpatientEncounter with (["Medication, Discharge": "Schedule II, III and IV Opioid Medications"]
                                                                                                                                                                                                                union ["Medication, Discharge": "Schedule IV Benzodiazepines"]) OpioidOrBenzodiazepineDischargeMedication such that OpioidOrBenzodiazepineDischargeMedication.authorDatetime during day of InpatientEncounter.relevantPeriod */ WITH _InpatientEncounter AS
  (SELECT *
   FROM {{ ref('SafeUseofOpioidsConcurrentPrescribingInpatient_Encounter_with_Age_Greater_than_or_Equal_to_18') }}),
                                                                                                                                                                                                                                                                                                                                                                                                                                                     OpioidOrBenzodiazepineDischargeMedication AS (({{ transpilerRetrieve('2.16.840.1.113762.1.4.1046.241', 'qdm', 'medication_discharge', 'v56', 'code') }})
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   UNION ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1125.1', 'qdm', 'medication_discharge', 'v56', 'code') }}))
SELECT *
FROM
  (SELECT InpatientEncounter.*
   FROM _InpatientEncounter AS InpatientEncounter
   LEFT JOIN OpioidOrBenzodiazepineDischargeMedication USING (patientId)
   WHERE (OpioidOrBenzodiazepineDischargeMedication.authorDatetime BETWEEN InpatientEncounter.relevantPeriod.low AND InpatientEncounter.relevantPeriod.high))