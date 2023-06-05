package gov.va.sparkcql.binding

import scala.reflect.runtime.universe._
import gov.va.sparkcql.dataprovider.DataProvider
import gov.va.sparkcql.model.{IdentifiedLibraryContent}
import gov.va.sparkcql.converter.{Converter, FileContentToIdentifiedLibraryContent}

trait Binding {
  val boundType: Type
  val converter: Option[Converter]
}

case class LibraryFileBinding(path: String) extends Binding {
  val boundType = typeOf[IdentifiedLibraryContent]
  val converter = Some(new FileContentToIdentifiedLibraryContent())
}

case class LibraryTableBinding(schema: Option[String], table: String) extends Binding {
  val boundType = typeOf[IdentifiedLibraryContent]
  val converter = None
}

trait ClinicalBindable

case class ClinicalTableBinding(schema: Option[String], table: String) extends Binding {
  val boundType = typeOf[ClinicalBindable]
  val converter = None
}