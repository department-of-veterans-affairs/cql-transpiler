# imports must be set up automatically
from functools import reduce
import pandas as pd
from pyspark.sql import DataFrame
from pyspark.sql import SparkSession
from pyspark.sql.functions import lit
from user_provided_data import UserProvidedData
from model.encounter import Encounter
from model.patient import PatientModel
from model.model_info import ModelInfo

# always present
class CQLValue():

    # Literals are always stored in CQLSessions. lists and tuples should contain only other CQLSessions
    def __init__(self, name, literalValue = None, listValue = None, tupleValue = None, dataFrameValue = None, variableValue = None):
        self.name = name
        self.literalValue = literalValue
        self.listValue = listValue
        self.tupleValue = tupleValue
        self.dataFrameValue = dataFrameValue
        self.variableValue = variableValue
    
    def getValue(self):
        if (self.literalValue != None):
            return self.literalValue
        if (self.listValue != None):
            return self.listValue
        if (self.tupleValue != None):
            return self.tupleValue
        if (self.dataFrameValue != None):
            return self.dataFrameValue
        if (self.variableValue != None):
            return self.variableValue.getValue()
        return None
    
    def asDataFrame(self, sparkSession: SparkSession, name = '') -> DataFrame:
        if len(name) == 0:
            name = self.name
        if (self.dataFrameValue != None):
            return self.dataFrameValue
        if (self.literalValue != None):
            df = pd.DataFrame.from_records([{name + '.value': self.literalValue}])
            return sparkSession.createDataFrame(df)
        if (self.listValue != None):
            dataFrameList = [listMember.asDataFrame(sparkSession, name = name + '.').withColumn(name + '.index', lit(listIndex)) for listIndex,listMember in enumerate (self.listValue)]
            return reduce(lambda a, b: a.unionByName(b, allowMissingColumns = True), dataFrameList)
        if (self.tupleValue != None):
            dataFrameList = [value.asDataFrame(sparkSession, name = name + '.' + key) for key, value in self.tupleValue.items()]
            return reduce(lambda a, b: a.unionByName(b, allowMissingColumns = True), dataFrameList)
        if (self.variableValue != None):
            return self.variableValue.asDataFrame(sparkSession, name = name)
        return sparkSession.createDataFrame(pd.DataFrame.from_records([{'error': 'not set'}]))
    
    def asStrings(self, sparkSession: SparkSession) -> list[str]:
        return self.asDataFrame(sparkSession).toJSON().collect()

# always present
def models(modelSource: str) -> dict[str, ModelInfo]:
    # TODO: populate models based off a source, e.g. FHIR 4.0.1,
    # TODO: models and model names should be generates/accessed based off imported model info files
    return {'Encounter': Encounter(), 'Patient': PatientModel()}

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