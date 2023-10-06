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

'''
To work exclusively with DataFrames, we'd have to have a dataframe equivalents for:
 * <def variableName: someLiteral>
 * <def variableName: {columnName: someLiteral}>
 * <def variableName: {someLiteral}>
This is the simplest possible dataframe:
 <Table variableName>
 |value      |
 |///////////|
 |someLiteral|
We can transform <def variableName: someLiteral> into the above table and build from there.
 def variableName: {columnName: someLiteral}
 ->
  <Table variableName>
   |value                    |
   |/////////////////////////|
   |{columnName: someLiteral}|

 def variableName: {someLiteral}
 ->
  <Table variableName>
   |value        |
   |/////////////|
   |{someLiteral}|


If we take this approach, this tabular structure on the database:
 <Table SomeTable>
 |   a|   b|...
 |////|////|...
 |r1v1|r1v2|...
 |r2v1|r2v2|...
 .
 .
 .
retrieved via <def someVariable: [SomeTable]>
...must be equivalent to the CQL <def SomeTable: {{a: r1v1, b: r1v2, ...}, {a: r2v1, b: r2v2, ...}, ...}>
...must be transformed into the DataFrame
 <Table SomeTable>
 |value                                                  |
 |{{a: r1v1, b: r1v2, ...}, {a: r2v1, b: r2v2, ...}, ...}|

The access <def access: [SomeTable] T return T.a> must therefore result in the dataframe
 |value           |
 |{r1v1, r2v1,...}|
'''

# always present
def models(modelSource: str) -> dict[str, ModelInfo]:
    # TODO: populate models based off a source, e.g. FHIR 4.0.1,
    # TODO: models and model names should be generates/accessed based off imported model info files
    return {'Encounter': Encounter(), 'Patient': Patient()}

# always present
def retrieveWithContextFilter(spark: SparkSession, userProvidedData: UserProvidedData, modelSource: str, model: str, currentContext = None) -> DataFrame:
    df = spark.table(model)
    if currentContext != None:
        df = df.filter(df[models(modelSource)[model].getIdColumnName()] == userProvidedData.getModelContextID(model))
    return df

'''
Always present.

Maps tables in the format
|a   |b   |...
|/////////|...
|r1v1|r1v2|...
|r2v1|r2v2|...
.
.
.
To the format
|value                                                |
|/////////////////////////////////////////////////////|
|{{a: r1v1, b: r1v2,...}, {a: r2v1, b: r2v2,...}, ...}|
'''
def mapSourceTableToDataFrame(dataFrame: DataFrame) -> DataFrame:
    listOfNameColumnPairs = [[lit(column_name), dataFrame[column_name]] for column_name in dataFrame.columns]
    flatListOfNameColumnPairs = [item for sublist in listOfNameColumnPairs for item in sublist]
    thinDF = dataFrame.withColumn("value", create_map(flatListOfNameColumnPairs))
    crushedDF = thinDF.agg(collect_list("value").alias("value"))
    return crushedDF

# using FHIR '4.0.1'
# context Patient
# define retrieved: [Patient]
def retrieved(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> DataFrame:
    return mapSourceTableToDataFrame(retrieveWithContextFilter(sparkSession, userProvidedData, "FHIR '4.0.1'", "Patient", "Patient"))

# define a: 1
def a(sparkSession: SparkSession, userProvidedData: UserProvidedData) -> DataFrame:
    return sparkSession.createDataFrame([[1]], ["value"])