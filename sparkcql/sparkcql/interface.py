from abc import ABC as _ABC
from abc import abstractmethod as _abstractmethod
from pyspark.sql.dataframe import DataFrame
from .model.fhir import Identifier

class Binding(_ABC):
    @_abstractmethod
    def resolve(self, id: str) -> DataFrame:
        pass