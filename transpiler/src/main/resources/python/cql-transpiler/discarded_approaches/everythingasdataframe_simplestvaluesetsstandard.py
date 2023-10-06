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
...nmust be equivalent to the CQL <def SomeTable: {{a: r1v1, b: r1v2, ...}, {a: r2v1, b: r2v2, ...}, ...}>
...must be transformed into the dataframe
|value                                                  |
|{{a: r1v1, b: r1v2, ...}, {a: r2v1, b: r2v2, ...}, ...}|
'''