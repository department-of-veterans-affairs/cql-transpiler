# imports must be set up automatically
import pandas as pd
from pyspark.sql import DataFrame
from pyspark.sql import SparkSession
from pyspark.sql.functions import lit
from cql_value import CQLValue
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

# set variable to literal
# define a: 1
'''
as table:
|a.value|
|///////|
|      1|
'''
def a(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> CQLValue:
    return CQLValue(name = 'a', literalValue = 1)

# set variable to variable
# define b: a
'''
as table:
|b.value|
|///////|
|      1|
'''
def b(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> CQLValue:
    return CQLValue(name = 'b', variableValue = a(sparkSession, userProvidedData))

# set variable to simple list
# define c: {a, b}
'''
as table:
|c..value|c.index|
|////////|///////|
|       1|      0|
|       1|      1|
'''
def c(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> CQLValue:
    return CQLValue(name = 'c', listValue = {a(sparkSession, userProvidedData), b(sparkSession, userProvidedData)})

# set variable to nested list
# define d: {c, c}
'''
as table:
|d...value|d.index|d..index|
|/////////|///////|////////|
|        1|      0|       0|
|        1|      0|       1|
|        1|      1|       0|
|        1|      1|       1|
'''
def d(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> CQLValue:
    return CQLValue(name = 'd', listValue = {c(sparkSession, userProvidedData), c(sparkSession, userProvidedData)})

# simple tuple
# define e: {w: a, x: b}
'''
as table:
|e.w.value|e.x.value|
|/////////|/////////|
|        1|         |
|         |        1|
'''
def e(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> CQLValue:
    return CQLValue(name = 'e', tupleValue = {'w': a(sparkSession, userProvidedData), 'x': b(sparkSession, userProvidedData)})

# complex tuple
# define f: {y: e, z: c}
'''
as table:
|f.y.w.value|f.y.x.value|f.z..value|f.z..index|
|///////////|///////////|//////////|//////////|
|          1|           |          |          |
|           |          1|          |          |
|           |           |         1|         0|
|           |           |         1|         1|
'''
def f(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> CQLValue:
    return CQLValue(name = 'f', tupleValue = {'y': e(sparkSession, userProvidedData), 'z': c(sparkSession, userProvidedData)})
'''
# using FHIR 4.0.1
# context Patient
# define "Patient Value": [Patient]
def Patient_Value1234567890(spark: SparkSession, userProvidedData: UserProvidedData) -> CQLValue:
    return CQLValue(dataFrameValue = retrieveWithContextFilter(spark, userProvidedData, 'FHIR 4.0.1', 'Patient', 'Patient'))
'''