/*
define "Numerator":
  /*Encounters of patients prescribed two or more opioids or an opioid and benzodiazepine at discharge.
  */ ("Inpatient Encounters with an Opioid or Benzodiazepine at Discharge" InpatientEncounter
      where (Count(["Medication, Discharge": "Schedule II, III and IV Opioid Medications"] Opioids
                   where Opioids.authorDatetime during day of InpatientEncounter.relevantPeriod return distinct Opioids.code ) >= 2)
        or exists (["Medication, Discharge": "Schedule II, III and IV Opioid Medications"] OpioidsDischarge
                   where OpioidsDischarge.authorDatetime during day of InpatientEncounter.relevantPeriod
                     and exists ["Medication, Discharge": "Schedule IV Benzodiazepines"] BenzodiazepinesDischarge where BenzodiazepinesDischarge.authorDatetime during day of InpatientEncounter.relevantPeriod ) ) */
SELECT *
FROM
  (SELECT *
   FROM {{ ref('SafeUseofOpioidsConcurrentPrescribingInpatient_Encounters_with_an_Opioid_or_Benzodiazepine_at_Discharge') }}) AS InpatientEncounter
WHERE ((count
          (SELECT (Opioids.code)
           FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1046.241', 'qdm', 'medication_discharge', 'v56', 'code') }}) AS Opioids
           WHERE (Opioids.authorDatetime BETWEEN InpatientEncounter.relevantPeriod.low AND InpatientEncounter.relevantPeriod.high)) >= 2)
       OR EXISTS
         (SELECT *
          FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1046.241', 'qdm', 'medication_discharge', 'v56', 'code') }}) AS OpioidsDischarge
          WHERE ((OpioidsDischarge.authorDatetime BETWEEN InpatientEncounter.relevantPeriod.low AND InpatientEncounter.relevantPeriod.high)
                 AND EXISTS
                   (SELECT *
                    FROM ({{ transpilerRetrieve('2.16.840.1.113762.1.4.1125.1', 'qdm', 'medication_discharge', 'v56', 'code') }}) AS BenzodiazepinesDischarge
                    WHERE (BenzodiazepinesDischarge.authorDatetime BETWEEN InpatientEncounter.relevantPeriod.low AND InpatientEncounter.relevantPeriod.high)))))