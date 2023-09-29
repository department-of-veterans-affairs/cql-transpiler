from pyspark.sql import SparkSession
from pyspark.sql import DataFrame
from model.patient_model_info import PatientModelInfo
from generated import runGeneratedScript

# 'type' column should be appended if coming from a multi-table relational structure
data = [{ 'patientID': 1, 'type': 'patient', 'name': 'foo' },
        { 'patientID': 2, 'type': 'patient', 'name': 'bar' },
        { 'encounterID': 1, 'type': 'encounter', 'patientID': 1, 'details': 'foo' },
        { 'encounterID': 2, 'type': 'encounter', 'patientID': 1, 'details': 'bar' },
        { 'encounterID': 3, 'type': 'encounter', 'patientID': 2, 'details': 'baz' }]
spark = SparkSession.Builder().getOrCreate()
df = spark.createDataFrame(data)
# TODO need to supply context and ID programatically
runGeneratedScript(PatientModelInfo(), 1, df, spark)