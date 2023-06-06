package gov.va.sparkcql.model

import scala.reflect.runtime.universe._
import gov.va.sparkcql.dataprovider.DataProvider
import org.apache.spark.sql.{Dataset, Row}
import org.apache.spark.sql.SparkSession

trait BindableData

case class LibraryData(identifier: VersionedIdentifier, content: String) extends BindableData
case class ValueSetData(name: String, expansion: Seq[String]) extends BindableData
trait ClinicalData extends BindableData {
  val dataType: QName
  val value: Dataset[Row]
}

case class Binding[+BindableData : TypeTag](partialProvider: SparkSession => DataProvider) {
  val typeTag = typeOf[BindableData]
}