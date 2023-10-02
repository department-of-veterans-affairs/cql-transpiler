# imports are set up automatically
import pandas as pd
from pyspark.sql import DataFrame
from pyspark.sql import SparkSession
from pyspark.sql.functions import lit
from user_provided_data import UserProvidedData
from model.encounter import Encounter
from model.patient import Patient
from model.model_info import ModelInfo

# always present
def models(source: str) -> dict[str, ModelInfo]:
    # TODO: populate models based off a source, e.g. FHIR 4.0.1,
    # TODO: models and model names should be generates/accessed based off imported model info files
    return {'Encounter': Encounter(), 'Patient': Patient()}

# always present
def contextFilter(spark: SparkSession, userProvidedData: UserProvidedData, df: DataFrame, contextModel: ModelInfo) -> DataFrame:
    try:
        if df == spark.table(contextModel.getName()):
            return df.filter(df[contextModel.getIdColumnName()] == lit(userProvidedData.getModelContextID(contextModel.getName())))
    except:
        pass
    return df

# define a: 1
def a(spark: SparkSession, userProvidedData: UserProvidedData) -> DataFrame:
    return spark.createDataFrame(pd.DataFrame.from_records({'literalvalue': 1}, index=[0]))

# define mytuple: { 'b': a, 'c': 'foo' }
def mytuple(spark: SparkSession, userProvidedData: UserProvidedData) -> DataFrame:
    # TODO
    return spark.createDataFrame(pd.DataFrame.from_records({'b': 1, 'c': 'foo'}, index=[0]))

# define "Patient Value": [Patient]
def Patient_Value1234567890(spark: SparkSession, userProvidedData: UserProvidedData) -> DataFrame:
    # using FHIR 4.0.1
    # context Patient
    contextModel = models('FHIR 4.0.1')['Patient']
    # [Patient]
    df = spark.table('Patient')
    return contextFilter(spark, userProvidedData, df, contextModel)