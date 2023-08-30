import pytest
import os
import json
from glob import glob
from pyspark.sql.functions import from_json, col, explode, from_json, expr
from pyspark.context import SparkContext
from pyspark.conf import SparkConf
from pyspark.sql import SparkSession, Row, DataFrame
from sparkcql.data_binding import DataBinding, DataBindingConfiguration, DataBindingType
from runtime_fixtures import spark, load_resource

class MockDataBinding(DataBinding):
    def __init__(self, spark: SparkSession, binding: DataBindingConfiguration, bundles: list[str], elms: list[str]):
        #df_bundles = spark.createDataFrame([Row(bundle=bundle) for bundle in bundles])
        df_bundles = spark.read.json(spark.sparkContext.parallelize(bundles))
        df_entries = df_bundles.select(explode(col("entry")).alias("entry"))
        for type in binding.get("type"):
            self._mount(df_entries, type)
        df_entries.show()

    def resolve(self, id: str) -> DataFrame:
        return None

    def _mount(self, df_entries: DataFrame, type: DataBindingType):
        primary_codepath_index = "entry.resource." + config.primaryCodePath if config.primaryCodePath != None else "null"; # type: ignore
        #primary_startdate_index = primaryStartDate != null ? "entry.resource." + primaryStartDate : "null";
        #primary_enddate_index = primaryEndDate != null ? "entry.resource." + primaryEndDate : "null";
        df_datatype = df_entries \
            .withColumn("RowID", expr("uuid()")) \
            .withColumn("IndexPrimaryCodePath", expr(primary_codepath_index)) \
            .withColumn("IndexPrimaryStartDate", expr(primary_codepath_index)) \
            .withColumn("IndexPrimaryEndDate", expr(primary_codepath_index)) \
            .withColumn("ResourceData", expr("STRUCT(*)")) \
            .drop("entry") \
            .where(col("entry.resource.resourceType") == config.code) # type: ignore


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
def mock_binding(spark) -> MockDataBinding:
    bindings: DataBindingConfiguration = json.loads(load_resource("""binding_configuration.json""")[0])
    return MockDataBinding(spark, bindings, load_resource("""fhir\\bundle\\*.*"""), load_resource("""cql\\*.cql"""))