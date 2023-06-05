package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._

class TableDataProvider(schema: Option[String], table: String, resourceColumn: Option[String] = Some("resource")) extends DataProvider() {

  def initialize(spark: SparkSession): Unit = {
  }
  
  lazy val tableRef = {
    if (schema.isDefined)
      s"${schema.get}.${table}"
    else
      s"${table}"
  }

  def fetch[T <: Product](spark: SparkSession, filter: Option[List[FilterElement]])(implicit tag: TypeTag[T]): Dataset[T] = {
    import spark.implicits._
    if (resourceColumn.isDefined) {
      spark.table(table).select(col(s"${resourceColumn.getOrElse(resourceColumn.get)}.*")).as[T]
    } else {
      spark.table(table).as[T]
    }
  }

  def terminate(): Unit = {
  }
}