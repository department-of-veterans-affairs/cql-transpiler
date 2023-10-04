import pandas as pd
from pyspark.sql import DataFrame
from pyspark.sql import SparkSession
from pyspark.sql.functions import lit
from user_provided_data import UserProvidedData

'''
The statement
 > Y: 'Z'
where 'Y' is some literal, is converted to
 > Y: {'value': {'Z'}}
which translates to the following DataFrame:

Y
|value|
|    Z|
'''
# define a: 1
def a(spark: SparkSession, userProvidedData: UserProvidedData, prefix: str = '') -> DataFrame:
    # output is a result of hitting a Lteral
    return spark.createDataFrame(pd.DataFrame.from_records({prefix + 'value': 1}, index=[0]))

'''
The statements
 > Y: 'Z'
 > X: Y
where 'Z' is some literal, are converted to
 > Y: {'value': {'Z'}}
 > X: {'value': {'Z'}}
With 'Z' extracted for X, and are translated to the following DataFrames:

Y
|value|
|    Z|
X
|value|
|    Z|
'''
# define b: a
def b(spark: SparkSession, userProvidedData: UserProvidedData, prefix: str = '') -> DataFrame:
    # output is a result of calling an ExpressionRef
    return a(spark, userProvidedData, prefix)

'''
The statement
 > Y: {'Z', 'Z'}
where 'Z' is some literal, is converted to
 > Y: {'Y': {'Z', 'Z'}}
and is translated to the following DataFrame:

Y
|value|
|    Z|
|    Z|
'''
# define c: {b, b}
def c(spark: SparkSession, userProvidedData: UserProvidedData, prefix: str = '') -> DataFrame:
    # Lists are a sequence of joined Literals/ExpressionRefs. duck typing means overlapping columns are condensed, thus <.drop()>
    return b(spark, userProvidedData, prefix).join(b(spark, userProvidedData, prefix)).drop()

# define d: {key: c}
def d(spark: SparkSession, userProvidedData: UserProvidedData, prefix: str = '') -> DataFrame:
    # same as a retrieval with an expression ref, except this time the value is named, since the values inside an equivalent CQL tuple will always be accessed with their keys
    return c(spark, userProvidedData, prefix + 'key.')

'''
For
 > Y: 'Z'
 > X: Y
The statement 
 > W: {A: X, B: Y}
 is converted to
 > W: {A: {value: {'Z'}}, B: {value: {'Z'}}}
and is translated to the following DataFrame:

W
|A.value|B.value|
|      Z|       |
|       |      Z|
'''
# define e: {w: a, x: b, y: c, z: d}
def e(spark: SparkSession, userProvidedData: UserProvidedData, prefix: str = '') -> DataFrame:
    return a(spark, userProvidedData, prefix + 'w.').join(b(spark, userProvidedData, prefix + 'x.')).join(c(spark, userProvidedData, prefix + 'y.')).join(d(spark, userProvidedData, prefix + 'z.'))