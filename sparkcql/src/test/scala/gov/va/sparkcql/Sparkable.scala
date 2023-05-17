package gov.va.sparkcql

import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}

trait Sparkable {
  lazy val spark = {
    org.apache.log4j.LogManager.getRootLogger().setLevel(Level.OFF)  
    SparkSession.builder()
      .master("local[4]")
      .getOrCreate()
  }
}