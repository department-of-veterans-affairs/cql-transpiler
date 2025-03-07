/*
define "Initial Population":
  "Inpatient Encounters with an Opioid or Benzodiazepine at Discharge"
*/
SELECT *
FROM {{ ref('SafeUseofOpioidsConcurrentPrescribingInpatient_Encounters_with_an_Opioid_or_Benzodiazepine_at_Discharge') }}