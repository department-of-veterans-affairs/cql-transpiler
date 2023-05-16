package gov.va.sparkcql

import org.apache.spark.sql.SparkSession

trait Sparkable {
  def fixture = new {
    val spark = SparkSession.builder()
      .master("local[1]")
      .getOrCreate()
  }
}
