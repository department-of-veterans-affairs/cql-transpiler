import pandas as pd
from user_provided_data import UserProvidedData
from pyspark.sql import SparkSession

# Manually set up database data for testing
#
#
#
patientData = pd.DataFrame.from_records([{ 'patientID': 1, 'name': 'foo', 'addresses': ['baz', 'foobar']},
        { 'patientID': 2, 'name': 'bar', 'addresses': ['baz', 'foobar'] }])
encounterData = pd.DataFrame.from_records([{ 'encounterID': 1, 'patientID': 1, 'details': 'foo', 'period': { 'start': '1990', 'end': '2000'}, 'status': { 'value': 'finished' }},
        { 'encounterID': 2, 'patientID': 1, 'details': 'bar', 'period': { 'start': '2000', 'end': '2010'}, 'status': { 'value': 'planned' }},
        { 'encounterID': 3, 'patientID': 2, 'details': 'baz', 'period': { 'start': '2010', 'end': '2020'}, 'status': { 'value': 'planned' }}])
spark = SparkSession.Builder().getOrCreate()
patientDF = spark.createDataFrame(patientData)
patientDF.createOrReplaceTempView('Patient')
encounterDF = spark.createDataFrame(encounterData)
encounterDF.createOrReplaceTempView('Encounter')

# Manually set up user provided data
#
#
#
userProvidedData = UserProvidedData()
userProvidedData.setModelContextID("Patient", 1)

# Use generated sql to query session
#
#
# spark.sql('SQL GOES HERE').show()