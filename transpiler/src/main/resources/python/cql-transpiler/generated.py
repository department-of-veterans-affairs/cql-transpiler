from pyspark.sql import SparkSession
from pyspark.sql import DataFrame
from pyspark.sql.functions import lit
from model.model_info import ModelInfo
from model.patient_model_info import PatientModelInfo
from model.encounter_model_info import EncounterModelInfo
import pyspark.sql.functions as functions

def runGeneratedScript(context: ModelInfo, id, df: DataFrame, spark: SparkSession):
    # using FHIR 4.0.1
    patientModelInfo = PatientModelInfo()
    encounterModelInfo = EncounterModelInfo()
    # context Patient
    if id is None:
        print("Context defined. Must provide ID.")
        quit()
    context = patientModelInfo
    df.filter(df[context.getIdColumnName()] == lit(id))
    # define a: 1
    df = df.withColumn('a', lit(1))
    # define mytuple: { 'b': 1, 'c': 'foo' }
    df = df.withColumn('mytuple.a', lit(1)).withColumn('mytuple.b', lit('foo'))
    # define mytuple: { 'd': 2, 'internal': {'e': [Encounter]} }
    df = df.withColumn('mytuple.c', lit(2))
    # TODO
    '''
    Original CQL:
    <
    context x
    define c: [y]
    >

    Translated Pyspark:
    <
    ?
    >

    Original data:
    {
        entries = [
            {
                id: "1",
                type: "x"
            },
            {
                "id": "1",
                "xid": "1",
                "type": "y"
                "details": "a"
            },
            {
                "id": "2",
                "xid": "1",
                "type": "y"
                "details": "b"
            }
        ]
    }
    
    Data on database:
    
    Table x:
    |id|
    |__|
    | 1|

    Table y:
    |id|xId|details|
    |______________|
    | 1| 1|       a|
    | 1| 2|       b|

    Merged Tables as DataFrame:
    |x.id|type|y.id|y.details|
    |________________________|
    |   1|   x|    |         |
    |   1|   y|   1|        a|
    |   1|   y|   2|        b|

    Dataframe after applying transformations to get CQL result:
    |x.id|type| y.id|y.details|c.1.x.id|c.1.type|c.1.y.id|c.1.y.details|c.2.x.id|c.2.type|c.2.y.id|c.2.y.details|
    |___________________________________________________________________________________________________________|
    |   1|   x|     |         |       1|      y|        1|            a|       1|      y|        2|            b|

    '''
    # [because 'context Patient' is present]
    df.filter(df[context.getTypeColumnName()] == lit(context.getType()))
    df.printSchema()
    df.show()