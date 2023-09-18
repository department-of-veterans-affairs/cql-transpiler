/*
library UC_Simple_Encounter version '1.0'
*/
CREATE VIEW Library__UC_Simple_Encounter AS
SELECT
    'UC_Simple_Encounter' LibraryName,
    '1.0' LibraryVersion

/*
using QDM version '5.6'
*/
CREATE VIEW QDM__Encounter_Performed AS
SELECT * 
FROM QDM_5_6.Encounter_Performed
-- ... other QDM data references here

/*
valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'
*/
CREATE VIEW ValueSet__Emergency_Department_Visit
SELECT VS.*
FROM Terminology__ValueSet VS
INNER JOIN 
WHERE URN = 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'

/*
parameter "Measurement Period" Interval<DateTime>
*/
CREATE VIEW Parameter__Measurement_Period AS
SELECT 
    -- Template placeholder would get resolved with runtime parameter
    {{ MeasureStartDate }} StartDate,       -- e.g. '01/01/2023'
    {{ MeasureEndDate }} EndDate            -- e.g. '12/31/2023'

/*
define "ED Encounter":
  ["Encounter, Performed": "Emergency Department Visit"] E
  where E.relevantPeriod ends during day of "Measurement Period"
*/
CREATE VIEW UC_Simple_Encounter__ED_Encounter AS
SELECT E.*
FROM QDM56__Encounter_Performed E
WHERE 
    e.code IN (SELECT code FROM ValueSet__Emergency_Department_Visit)
    AND E.endDate >= DATE(SELECT StartDate FROM Parameter__Measurement_Period)
    AND E.endDate < DATE_ADD((SELECT EndDate FROM Parameter__Measurement_Period), 1)