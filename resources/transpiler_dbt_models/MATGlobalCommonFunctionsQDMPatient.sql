/*
context Patient
*/
SELECT *
FROM ({{ transpilerRetrieve(none, 'qdm', 'patient', 'v56', 'None') }}) AS SingletonFrom/* error -- No alias context defined; unable to narrow down SingletonFrom */
LIMIT 1