package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import org.hl7.elm.r1.CodeFilterElement
import org.hl7.elm.r1.DateFilterElement
import org.hl7.elm.r1.OtherFilterElement
import gov.va.sparkcql.model.DataTypeRef

sealed trait FilterElement
final case class CodeFilter() extends CodeFilterElement with FilterElement
final case class DateFilter() extends DateFilterElement with FilterElement
final case class OtherFilter() extends OtherFilterElement with FilterElement

abstract class DataProvider() {

  def fetch(dataType: DataTypeRef, spark: SparkSession): Dataset[Row]

  def fetch[T <: Product : TypeTag](spark: SparkSession): Dataset[T] = {
    fetch[T](None, spark)
  }

  def fetch[T <: Product : TypeTag](filter: Option[List[FilterElement]], spark: SparkSession): Dataset[T]
}