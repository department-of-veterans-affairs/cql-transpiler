from pyspark.sql import SparkSession
from pyspark.sql import DataFrame
from pyspark.sql.functions import lit

patientItem = { "name": "foo", "encounter": "bar" }
spark = SparkSession.builder.getOrCreate()
patientDF = spark.createDataFrame([patientItem, patientItem]);
# define a: 1
modifiedDF = patientDF.withColumn("a", lit(1))
# define mytuple: { "a": 1, "b": "foo" }
modifiedDF = modifiedDF.withColumn("mytuple.a", lit(1)).withColumn("mytuple.b", lit("foo"))
# define mytuple: { "c": 2, "internal": {"e": [Encounter]} }
modifiedDF = modifiedDF.withColumn("mytuple.c", lit(2)).withColumn("mytuple.internal.e", ???)
modifiedDF.printSchema()
modifiedDF.show()