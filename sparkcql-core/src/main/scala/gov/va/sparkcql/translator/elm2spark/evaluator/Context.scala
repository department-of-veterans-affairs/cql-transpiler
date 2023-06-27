package gov.va.sparkcql.translator.elm2spark.evaluator

import org.apache.spark.sql.SparkSession

final case class Context(spark: SparkSession, parameters: Map[String, Object])