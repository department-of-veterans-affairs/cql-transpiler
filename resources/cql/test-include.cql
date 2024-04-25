library TestInclude version '1'

using QDM version '5.6'

valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'

/*returns list of all locations within an encounter, including locations for immediately prior ED visit.*/
define function "HospitalizationLocations"(Encounter "Encounter, Performed" ):
  Encounter Visit
  	let EDVisit: Last(["Encounter, Performed": "Emergency Department Visit"] LastED
  			where LastED.relevantPeriod ends 1 hour or less on or before start of Visit.relevantPeriod
  			sort by 
  			end of relevantPeriod
  	)
  	return if EDVisit is null then Visit.facilityLocations 
  		else flatten { EDVisit.facilityLocations, Visit.facilityLocations }