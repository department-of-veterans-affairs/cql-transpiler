package gov.va.sparkcql.translator.elm2spark

import org.apache.spark.sql.{DataFrame, Column}
import javax.xml.namespace.QName

trait ResultType

case class QuerySourceResult(
  dataType: QName,
  name: Option[String],
  df: DataFrame
) extends ResultType

case class ColumnResult(
  path: String,
  dataType: QName,
  scope: String,
  col: Column
) extends ResultType

// case class QueryResult(
//   name: Option[String],
//   df: DataFrame
// ) extends ResultType