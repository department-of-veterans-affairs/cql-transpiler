import pytest
import os
from glob import glob
from pyspark.context import SparkContext
from pyspark.conf import SparkConf
from pyspark.sql import SparkSession

@pytest.fixture
def spark() -> SparkSession:
    config = SparkConf()
    config.set("spark.sql.catalogImplementation", "in-memory")  # allows for local dev w/o installing winutils.exe
    context = SparkContext(master='local[1]', conf=config)
    return SparkSession(context).builder.getOrCreate()

def load_resource(relative_path: str) -> list[str]:
    resources: list[str] = []
    current_folder = os.path.dirname(os.path.abspath(__file__)) + "\\resources"
    full_path = os.path.join(current_folder, relative_path)
    for path in glob(full_path):
        with open(path, 'r') as file:
            resources.append(file.read())
            file.read()
    return resources