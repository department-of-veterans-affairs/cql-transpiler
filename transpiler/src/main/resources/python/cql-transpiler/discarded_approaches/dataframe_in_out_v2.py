from pyspark.sql.types import ArrayType, IntegerType, StructField, StructType
from functools import reduce
import pandas as pd
from pyspark.sql import DataFrame
from pyspark.sql import SparkSession
from pyspark.sql.functions import array, lit, to_json, struct, json_tuple, create_map, collect_list
from user_provided_data import UserProvidedData
from model.encounter import Encounter
from model.patient import PatientModel
from model.model_info import ModelInfo

'''
To work exclusively with DataFrames, we'd have to have a dataframe equivalents for:
 * <def variableName: someLiteral>
 * <def variableName: {someDefaultColumnName: someLiteral}>
 * <def variableName: {someLiteral}>
However, there is no way to make a DataFrame simpler than this one:
 Table variableName:
 |someDefaultColumnName|
 |/////////////////////|
 |          someLiteral|
 |someDefaultColumnName|
 |/////////////////////|
 |          [foo]|
 
 name|value
 1   | foo
 2   | bar
 def var: [Encounter] -> [{name: encounter 1, value: foo}, {name: encounter 2, value bar}]
 def tuple: {a: 1, b: var}
 
If we transform all three of these statements into the above table, that means we're saying all three of those statements are equal to each other, and we're saying that they're all equal to:
 * <def variableName: {{someDefaultColumnName: someLiteral}}>
But, this transformation makes it impossible to disambiguate between list nesting levels. For example, <def X: 1>, <def X: {1}}>, and <def X: {{1}}> all resolve to the dataframe
 <DataFrame X>
 |value|
 |/////|
 |    1|

So this approach is unusuable.

I've included what an implementation of what this approach would look like below
'''
def T(sparkSession: SparkSession, columnName: str = "value", dataFrameValue = None, listValue = None, tupleValue = None, literalValue = None) -> DataFrame:
    if dataFrameValue != None:
        return dataFrameValue
    if listValue != None:
        asTransformedListItems = map(lambda e: T(e), listValue)
        return reduce(lambda a, b: a.unionByName(b, allowMissingColumns = True), asTransformedListItems)
    if tupleValue != None:
        asTransformedListItems = map(lambda item: T(*item), tupleValue.items)
        return reduce(lambda a, b: a.join(b).drop(), asTransformedListItems)
    # otherwise, x is a literal
    return sparkSession.createDataFrame([[literalValue]], [columnName])

# <def a: 1>
def a(sparkSession: SparkSession):
    return T(sparkSession, literalValue = 1)

# <def b: a>
def b(sparkSession: SparkSession):
    return a(sparkSession)

# <def c: {a, b}>
def c(sparkSession: SparkSession):
    return T(sparkSession, listValue = {a(sparkSession), b(sparkSession)})
