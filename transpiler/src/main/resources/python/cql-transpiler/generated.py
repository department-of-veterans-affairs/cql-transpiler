# imports must be set up automatically
from pyspark.sql.types import ArrayType, IntegerType, StructField, StructType
from functools import reduce
import pandas as pd
from pyspark.sql import DataFrame
from pyspark.sql import SparkSession
from pyspark.sql.functions import array, lit, to_json, struct, json_tuple, create_map, collect_list
from user_provided_data import UserProvidedData
from model.encounter import Encounter
from model.patient import Patient
from model.model_info import ModelInfo

# always present
def models(modelSource: str) -> dict[str, ModelInfo]:
    # TODO: populate models based off a source, e.g. FHIR 4.0.1,
    # TODO: models and model names should be generates/accessed based off imported model info files
    return {'Encounter': Encounter(), 'Patient': Patient()}

# always present
def retrieveWithContextFilter(spark: SparkSession, userProvidedData: UserProvidedData, modelSource: str, model: str, currentContext: str) -> DataFrame:
    df = spark.table(model)
    if (model == currentContext):
        df = df.filter(models(modelSource)[model].getIdColumnName() == userProvidedData.getModelContextID(model))
    return df

# def a: 1
'''
|value|
|/////|
|    1|
'''
def a(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> DataFrame:
    return sparkSession.createDataFrame([[1]], ["value"])


# def b: {a, 1}
'''

|value|
|/////|
|    1| 

+


|value|
|/////|
|    1|

|
v

|value|
|/////|
|    1|
|    1|

|
v

|value    |
|/////////|
|[value->1]|
|[value->1]|

||

|value                   |
|////////////////////////|
|[[value->1], [value->1]]|
'''
def b(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> DataFrame:
    df = a(sparkSession, userProvidedData).unionByName(sparkSession.createDataFrame([[1]], ["value"]), True)
    listOfNameColumnPairs = [[lit(column_name), df[column_name]] for column_name in df.columns]
    flatListOfNameColumnPairs = [item for sublist in listOfNameColumnPairs for item in sublist]
    df = df.withColumn("value", create_map(flatListOfNameColumnPairs))
    df = df.agg(collect_list("value").alias("value"))
    return df
