import pandas as pd
from user_provided_data import UserProvidedData
from pyspark.sql import SparkSession
from generated import Patient_Value1234567890, a, mytuple

# Manually set up database data for testing
#
#
#
patientData = pd.DataFrame.from_records([{ 'patientID': 1, 'name': 'foo' },
        { 'patientID': 2, 'name': 'bar' }])
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
userProvidedData.setModelContextID("Patient", 123)

# Query generated functions for any desired data
#
#
#
a(spark, userProvidedData).show()
mytuple(spark, userProvidedData).show()
Patient_Value1234567890(spark, userProvidedData).show()