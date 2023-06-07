package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import gov.va.sparkcql.model.DataTypeRef

class TableDataProvider(schema: Option[String], table: String, resourceColumn: Option[String]) extends DataProvider {
  def createAdapter(spark: SparkSession): DataAdapter = {
    new TableDataAdapter(spark, schema, table, resourceColumn)
  }
}

object TableDataProvider {
  def apply(schema: Option[String], table: String, resourceColumn: Option[String] = Some("resource")) = {
    new TableDataProvider(schema, table, resourceColumn)
  }
}