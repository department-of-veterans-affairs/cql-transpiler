package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._

class TableDataProvider(spark: SparkSession, schema: Option[String], table: String, resourceColumn: Option[String]) extends DataProvider(spark) {

  lazy val tableRef = {
    if (schema.isDefined)
      s"${schema.get}.${table}"
    else
      s"${table}"
  }

  def fetch[T <: Product : TypeTag](filter: Option[List[FilterElement]]): Dataset[T] = {
    import spark.implicits._
    if (resourceColumn.isDefined) {
      spark.table(table).select(col(s"${resourceColumn.getOrElse(resourceColumn.get)}.*")).as[T]
    } else {
      spark.table(table).as[T]
    }
  }
}

object TableDataProvider {
  def apply(schema: Option[String], table: String, resourceColumn: Option[String] = Some("resource")) 
    = (spark: SparkSession) => new TableDataProvider(spark, schema, table, resourceColumn)
}