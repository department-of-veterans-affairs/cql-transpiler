package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import gov.va.sparkcql.model.DataTypeRef

class TableDataProvider(schema: Option[String], table: String, resourceColumn: Option[String]) extends DataProvider() {

  lazy val tableRef = {
    if (schema.isDefined)
      s"${schema.get}.${table}"
    else
      s"${table}"
  }

  def fetch(dataType: DataTypeRef, spark: SparkSession): Dataset[Row] = {
    ???
  }

  def fetch[T <: Product : TypeTag](filter: Option[List[FilterElement]], spark: SparkSession): Dataset[T] = {
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
    = new TableDataProvider(schema, table, resourceColumn)
}