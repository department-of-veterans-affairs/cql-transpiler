from pyspark.sql import DataFrame
from model.unfiltered import Unfiltered
from model.model_info import ModelInfo

class UserProvidedData:
    _ids: dict[str, int] = {}
    
    def setModelContextID(self, modelName: str, id: int):
        self._ids[modelName] = id

    def getModelContextID(self, modelName: str) -> int:
        return self._ids[modelName]