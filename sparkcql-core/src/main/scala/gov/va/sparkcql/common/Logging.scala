package gov.va.sparkcql.common

import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}

object Logging {
  def setLoggingLevel(level: Level): Unit = {
    org.apache.log4j.LogManager.getRootLogger().setLevel(level)
    Logger.getLogger("org").setLevel(level)
  }
}
