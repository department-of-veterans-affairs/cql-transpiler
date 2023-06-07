package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import gov.va.sparkcql.model.DataTypeRef

class TableDataAdapter(spark: SparkSession, schema: Option[String], table: String, resourceColumn: Option[String]) extends DataAdapter(spark) {

  lazy val tableRef = {
    if (schema.isDefined)
      s"${schema.get}.${table}"
    else
      s"${table}"
  }

  def read(dataType: DataTypeRef, filter: Option[List[FilterElement]]): Dataset[Row] = {
    ???
  }

  def read[T <: Product : TypeTag](filter: Option[List[FilterElement]]): Dataset[T] = {
    import spark.implicits._
    if (resourceColumn.isDefined) {
      spark.table(table).select(col(s"${resourceColumn.getOrElse(resourceColumn.get)}.*")).as[T]
    } else {
      spark.table(table).as[T]
    }
  }
}