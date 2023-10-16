import pandas as pd
from user_provided_data import UserProvidedData
from pyspark.sql import SparkSession

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

# Use generated sql to query session
#
#
#
spark.sql("CREATE OR REPLACE VIEW a AS SELECT 1 _val")
spark.sql("CREATE OR REPLACE VIEW b AS SELECT collect_list(_val) FROM a ALIAS _val")
spark.sql("CREATE OR REPLACE VIEW c AS SELECT collect_list(_val) FROM (a union b) _val")
