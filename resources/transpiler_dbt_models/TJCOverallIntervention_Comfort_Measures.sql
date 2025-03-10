/*
define "Intervention Comfort Measures":
  ["Intervention, Order": "Comfort Measures"]
    union ["Intervention, Performed": "Comfort Measures"]
*/
SELECT *
FROM
  (SELECT *
   FROM ({{ transpilerRetrieve('1.3.6.1.4.1.33895.1.3.0.45', 'qdm', 'intervention_order', 'v56', 'code') }})) NATURAL
FULL JOIN
  (SELECT *
   FROM ({{ transpilerRetrieve('1.3.6.1.4.1.33895.1.3.0.45', 'qdm', 'intervention_performed', 'v56', 'code') }}))