package gov.va.sparkcql.binding

import gov.va.sparkcql.model.fhir.r4.{Coding, ValueSet, Period}
import gov.va.sparkcql.session.Session
import scala.reflect.runtime.universe._
import org.json4s._
import org.json4s.native.JsonMethods.{parse, compact}
import org.json4s.native.Serialization.write

trait BindingFactory {
  def create(session: Session, settings: Option[Map[String, Any]]): Binding
  def convertSettings[T : TypeTag](genericSettings: Option[Map[String, Any]])(implicit m: Manifest[T]): T = {
    implicit val formats = DefaultFormats
    val settingsJson = native.Serialization.write(genericSettings)
    parse(settingsJson).extract[T](formats, m)
  }
}