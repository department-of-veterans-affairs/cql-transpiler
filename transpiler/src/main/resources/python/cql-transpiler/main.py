import pandas as pd
from user_provided_data import UserProvidedData
from pyspark.sql import SparkSession

# Manually set up database data for testing
#
#
#
patientData = pd.DataFrame.from_records([{ 'patientID': 1, 'name': 'foo', 'addresses': ['baz', 'foobar']},
        { 'patientID': 2, 'name': 'bar', 'addresses': ['baz', 'foobar'] }])
encounterData = pd.DataFrame.from_records([{ 'encounterID': 1, 'patientID': 1, 'details': 'foo' },
        { 'encounterID': 2, 'patientID': 1, 'details': 'bar' },
        { 'encounterID': 3, 'patientID': 2, 'details': 'baz' }])
spark = SparkSession.Builder().getOrCreate()
patientDF = spark.createDataFrame(patientData)
patientDF.createOrReplaceTempView('Patient')
encounterDF = spark.createDataFrame(encounterData)
encounterDF.createOrReplaceTempView('Encounter')

# Manually set up user provided data
#
#
#
userProvidedData = UserProvidedData()
userProvidedData.setModelContextID("Patient", 1)

# Use generated sql to query session
#
#
#
spark.sql('SELECT collect_list(_val) AS _val FROM (SELECT collect_list(*) AS _val FROM (SELECT struct(*) FROM (SELECT * FROM Encounter)))').show()
'''
spark.sql('CREATE OR REPLACE VIEW a AS (SELECT 1 _val);')
spark.sql("CREATE OR REPLACE VIEW b AS (SELECT collect_list(_val) AS _val FROM a);")
spark.sql('CREATE OR REPLACE VIEW c AS (SELECT collect_list(_val) AS _val FROM ((SELECT 1 _val) UNION ALL (SELECT * FROM a)));')
spark.sql('CREATE OR REPLACE VIEW d AS (SELECT struct(foo, bar, baz) AS _val FROM (SELECT _val AS foo FROM b), (SELECT _val AS bar FROM c), (SELECT _val AS baz FROM (SELECT 1 _val)))')
spark.sql('CREATE OR REPLACE VIEW e AS (SELECT _val.foo FROM d AS _val);')
# We're using temp tables so references to them have to be temporary as well, but the actual conversion will not generate a temp view
spark.sql('CREATE OR REPLACE TEMP VIEW f AS (SELECT * FROM Encounter);')
spark.sql('CREATE OR REPLACE TEMP VIEW g AS (SELECT struct(*) AS _val FROM (SELECT _val as foo FROM (SELECT collect_list(*) AS _val FROM (SELECT struct(*) FROM f))), (SELECT _val AS bar FROM (SELECT 1 _val)));')
spark.sql('CREATE OR REPLACE TEMP VIEW h AS (SELECT _val.bar AS _val FROM g);')
spark.sql('CREATE OR REPLACE TEMP VIEW i AS (SELECT col.* FROM (SELECT explode(*) FROM (SELECT _val.foo AS  _val FROM g)))')
spark.sql('SELECT * FROM i').show()
'''