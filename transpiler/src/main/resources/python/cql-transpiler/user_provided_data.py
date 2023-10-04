from pyspark.sql import DataFrame
from model.unfiltered import Unfiltered
from model.model_info import ModelInfo

class UserProvidedData:
    _ids: dict = {}
    
    def setModelContextID(self, modelName: str, id):
        self._ids[modelName] = id

    def getModelContextID(self, modelName: str):
        return self._ids[modelName]