package gov.va.sparkcql.model

import scala.reflect.runtime.universe._
import gov.va.sparkcql.dataprovider.DataProvider
import org.apache.spark.sql.{Dataset, Row}

trait BindableData

// trait UntypedBindableData {
//   val value: Dataset[Row]
// }

case class LibraryData(identifier: VersionedIdentifier, content: String) extends BindableData
case class ValueSetData(identifier: VersionedIdentifier, content: String) extends BindableData
trait ClinicalData {
  val dataType: QName
  val value: Dataset[Row]
}

case class Binding[+BindableData](provider: DataProvider)