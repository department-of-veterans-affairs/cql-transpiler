package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import org.hl7.elm.r1.CodeFilterElement
import org.hl7.elm.r1.DateFilterElement
import org.hl7.elm.r1.OtherFilterElement
import org.hl7.elm.r1.Code

sealed trait FilterElement
final case class CodeFilter() extends CodeFilterElement with FilterElement
final case class DateFilter() extends DateFilterElement with FilterElement
final case class OtherFilter() extends OtherFilterElement with FilterElement

trait DataProvider {

  def fetch[T <: Product](spark: SparkSession)(implicit tag: TypeTag[T]): Dataset[T] = {
    fetch[T](spark, None)
  }

  def fetch[T <: Product](spark: SparkSession, filter: Option[List[FilterElement]])(implicit tag: TypeTag[T]): Dataset[T]

  def terminate(): Unit
}