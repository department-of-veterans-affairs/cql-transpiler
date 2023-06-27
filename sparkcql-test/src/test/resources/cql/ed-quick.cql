library ED_QUICK version '1.0'

using QUICK

// include MATGlobalCommonFunctions version '7.0.000' called Global

valueset "Admit Inpatient": 'urn:oid:2.16.840.1.113762.1.4.1111.164'
valueset "Decision to Admit to Hospital Inpatient": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.295'
valueset "Emergency Department Evaluation": 'urn:oid:2.16.840.1.113762.1.4.1111.163'
valueset "Emergency Department Visit": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.292'
valueset "Ethnicity": 'urn:oid:2.16.840.1.114222.4.11.837'
valueset "Hospital Settings": 'urn:oid:2.16.840.1.113762.1.4.1111.126'
valueset "ONC Administrative Sex": 'urn:oid:2.16.840.1.113762.1.4.1'
valueset "Payer": 'urn:oid:2.16.840.1.114222.4.11.3591'
valueset "Psychiatric/Mental Health Diagnosis": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.299'
valueset "Race": 'urn:oid:2.16.840.1.114222.4.11.836'

parameter "Measurement Period" Interval<DateTime>

context Patient

define "ED Encounter":
  ["Encounter": "Emergency Department Visit"] ED
  where ED.period ends after @2013-12-27T14:30
    // (@2013-12-27T14:30, @2013-12-28T14:30) ends during day of "Measurement Period"
    //where ED.period ends during day of "Measurement Period"
    //and ED.period ends before @2015-12-27T14:30
/*
define "Multi Query":
  from ["Encounter": "Emergency Department Visit"] ED1,
    ["Encounter": "Emergency Department Visit"] ED2,
    ["Encounter": "Emergency Department Visit"] ED3
      where ED1.period ends during day of "Measurement Period"
      and ED1.period ends before @2015-12-27T14:30

define "Tuple Test Def":
  ["Encounter": "Emergency Department Visit"] E
    return Tuple {projected: E.period, consumed: E.period}

define "Tuple Test Ref":
  "Tuple Test Def" EDref
    where EDref.projected ends during day of EDref.consumed
*/