from typing import TypedDict as _TypedDict
from pyspark.sql import SparkSession
from pyspark.sql.dataframe import DataFrame
from .model.fhir import Identifier, code

class DataBindingIndex(_TypedDict):
    column: str
    path: str

class DataBindingType(_TypedDict):
    code: code
    resourceColumn: str
    index: list[DataBindingIndex]

class DataBindingConfiguration(_TypedDict):
    system: str
    type: list[DataBindingType]

class DataBinding():
    def __init__(self, spark: SparkSession, binding: DataBindingConfiguration):
        self.spark = spark
        self.binding = binding

    def resolve(self, id: str) -> DataFrame:
        return self.spark.sql("SELECT [Indexes], [ResourceData] FROM TODO")
