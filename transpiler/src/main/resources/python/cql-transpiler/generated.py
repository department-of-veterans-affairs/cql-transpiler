# imports must be set up automatically

from pyspark.sql import DataFrame
from pyspark.sql import SparkSession
from user_provided_data import UserProvidedData
from model.encounter_model import EncounterModel
from model.patient_model import PatientModel
from model.model_info import ModelInfo

# always present
def models(modelSource: str) -> dict[str, ModelInfo]:
    # TODO: populate models based off a source, e.g. FHIR 4.0.1,
    # TODO: models and model names should be generates/accessed based off imported model info files
    return {'Encounter': EncounterModel(), 'Patient': PatientModel()}

# always present
def retrieve (spark: SparkSession, model: ModelInfo):
    return spark.table(model.getName())

# always present
def applyContext(spark: SparkSession, dataFrame: DataFrame, context: ModelInfo):
    if (context != None): 
        return dataFrame.join(retrieve(spark, context), on = context.getIdColumnName())
    return dataFrame

# always present
def filterContext(userData: UserProvidedData, dataFrame: DataFrame, context: ModelInfo):
    if (context != None): 
        return dataFrame.filter(dataFrame[context.getIdColumnName()] == userData.getModelContextID(context.getName()))
    return dataFrame

'''
define a: 1
'''
def a(spark: SparkSession, userData: UserProvidedData):
    return 1

'''
define b: {1, {foo: a}}
'''
def b(spark: SparkSession, userData: UserProvidedData):
    return [1, {'foo': a(spark, userData)}]

'''
using FHIR '4.0.1'
define retrieved: [Encounter]
'''
def c(spark: SparkSession, userData: UserProvidedData):
    return retrieve(spark,  models('http://hl7.org/fhir')['Encounter'])

'''
using FHIR '4.0.1'
context Patient
'''
def Patient(spark: SparkSession, userData: UserProvidedData):
    return retrieve(spark,  models('http://hl7.org/fhir')['Patient'])
