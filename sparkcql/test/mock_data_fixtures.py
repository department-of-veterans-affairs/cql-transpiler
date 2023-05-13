import pytest
import os
from glob import glob
from pyspark.sql.functions import from_json, col, explode, from_json
from pyspark.context import SparkContext
from pyspark.conf import SparkConf
from pyspark.sql import SparkSession, Row
from sparkcql.interface import Binding, DataFrame
from runtime_fixtures import spark, load_resource

class MockBinding(Binding):
    def __init__(self, spark: SparkSession, bundles: list[str], elms: list[str]):
        #df_bundles = spark.createDataFrame([Row(bundle=bundle) for bundle in bundles])
        df_bundles = spark.read.json(spark.sparkContext.parallelize(bundles))
        df_entries = df_bundles.select(explode(col("entry")).alias("entry"))
        df_entries.show()

    def resolve(self, id: str) -> DataFrame:
        return None

# @pytest.fixture
# def spark_db(spark: SparkSession):
#     #jstr1 = u'{"header":{"id":12345,"foo":"bar"},"body":{"id":111000,"name":"foobar","sub_json":{"id":54321,"sub_sub_json":{"col1":20,"col2":"somethong"}}}}'
#     #jstr2 = u'{"header":{"id":12346,"foo":"baz"},"body":{"id":111002,"name":"barfoo","sub_json":{"id":23456,"sub_sub_json":{"col1":30,"col2":"something else"}}}}'
#     #jstr3 = u'{"header":{"id":43256,"foo":"foobaz"},"body":{"id":20192,"name":"bazbar","sub_json":{"id":39283,"sub_sub_json":{"col1":50,"col2":"another thing"}}}}'
#     # df = SQLContext.createDataFrame([Row(json=jstr1),Row(json=jstr2),Row(json=jstr3)])

#     newJson = '{"Name":"something","Url":"https://stackoverflow.com","Author":"jangcy","BlogEntries":100,"Caller":"jangcy"}'
#     df = spark.read.json(spark.sparkContext.parallelize([newJson]))
#     df.show(truncate=False)    

#     # json_schema = spark.read.json(df.rdd.map(lambda row: row.json)).schema
#     # df.withColumn('json', from_json(col('json'), json_schema))
#     # df = spark.read.json(spark.createDataFrame(jstr1))
#     #df = spark.read.format("json").json()
#     #df.show()
#     # df = spark.read.format("csv").csv("D:\\Repo\\fast\\fast-db-cdw-mock\\data\\SPatient.csv")
#     # df.show()

@pytest.fixture
def mock_binding(spark) -> MockBinding:
    return MockBinding(spark, load_resource("""fhir\\bundle\\*.*"""), load_resource("""cql\\*.cql"""))