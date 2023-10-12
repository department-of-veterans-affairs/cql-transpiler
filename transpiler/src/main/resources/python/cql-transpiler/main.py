import pandas as pd
from user_provided_data import UserProvidedData
from pyspark.sql import SparkSession
from generated import *

# Manually set up database data for testing
#
#
#
patientData = pd.DataFrame.from_records([{ 'patientID': 1, 'name': 'foo', 'addresses': ['baz', 'foobar']},
        { 'patientID': 2, 'name': 'bar', 'addresses': ['baz', 'foobar'] }])
encounterData = pd.DataFrame.from_records([{ 'encounterID': 1, 'patientID': 1, 'details': 'foo' },
        { 'encounterID': 2, 'patientID': 1, 'details': 'bar' },
        { 'encounterID': 3, 'patientID': 2, 'details': 'baz' }])
spark = SparkSession.Builder().getOrCreate()
patientDF = spark.createDataFrame(patientData)
patientDF.registerTempTable('Patient')
encounterDF = spark.createDataFrame(encounterData)
encounterDF.registerTempTable('Encounter')

# Manually set up user provided data
#
#
#
userProvidedData = UserProvidedData()
userProvidedData.setModelContextID("Patient", 1)

# Query generated functions for any desired data
#
#
#
cVal = c(spark, userProvidedData)
PatientVal = Patient(spark, userProvidedData)
# context Patient
PatientVal.show()
'''
TODO: support context filtering properly... need to understand what that even looks like

context Patient
using FHIR '4.0.1'
'''
filterContext(userProvidedData, applyContext(spark, cVal, models('http://hl7.org/fhir')['Patient']), models('FHIR 4.0.1')['Patient']).show()