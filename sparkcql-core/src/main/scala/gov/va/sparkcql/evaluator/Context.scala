package gov.va.sparkcql.evaluator

import org.apache.spark.sql.SparkSession

final case class Context(parameters: Map[String, Object], spark: SparkSession)