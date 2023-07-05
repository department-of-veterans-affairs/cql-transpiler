package gov.va.sparkcql.translator

import org.apache.spark.sql.SparkSession

final case class Environment(parameters: Map[String, Object], spark: SparkSession)