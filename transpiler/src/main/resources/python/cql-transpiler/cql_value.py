import pandas as pd
from pyspark.sql import SparkSession, DataFrame
from functools import reduce
from pyspark.sql.functions import array, lit

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