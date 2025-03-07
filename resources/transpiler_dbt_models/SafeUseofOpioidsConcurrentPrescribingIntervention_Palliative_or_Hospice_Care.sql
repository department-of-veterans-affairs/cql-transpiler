/*
define "Intervention Palliative or Hospice Care":
  ["Intervention, Order": "Palliative or Hospice Care"]
    union ["Intervention, Performed": "Palliative or Hospice Care"]
*/
SELECT *
FROM
  (SELECT *
   FROM ({{ transpilerRetrieve('2.16.840.1.113883.3.600.1.1579', 'qdm', 'intervention_order', 'v56', 'code') }})) NATURAL
FULL JOIN
  (SELECT *
   FROM ({{ transpilerRetrieve('2.16.840.1.113883.3.600.1.1579', 'qdm', 'intervention_performed', 'v56', 'code') }}))