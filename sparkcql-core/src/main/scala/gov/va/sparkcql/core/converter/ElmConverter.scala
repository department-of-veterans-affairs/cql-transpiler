package gov.va.sparkcql.core.converter

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.converter.Converter._
import gov.va.sparkcql.core.Log
import org.hl7.elm
import javax.xml.namespace.QName

trait ElmConverter {

  implicit val literalToInt: Convertable[elm.r1.Literal, Int] = (s: elm.r1.Literal) => s.getValue().toInt

  implicit val literalToBoolean: Convertable[elm.r1.Literal, Boolean] = (s: elm.r1.Literal) => s.getValue().toBoolean
  
  implicit val literalToLong: Convertable[elm.r1.Literal, Long] = (s: elm.r1.Literal) => s.getValue().toLong

  implicit val literalToDouble: Convertable[elm.r1.Literal, Double] = (s: elm.r1.Literal) => s.getValue().toDouble

  implicit val literalToString: Convertable[elm.r1.Literal, String] = (s: elm.r1.Literal) => s.getValue()

  implicit val literalToScala: Convertable[elm.r1.Literal, Any] = (s: elm.r1.Literal) => {
    val value = s.getValue()

    s.getResultTypeName() match {
      case d: QName if d.getNamespaceURI() == "urn:hl7-org:elm-types:r1" && d.getLocalPart() == "Integer" => 
        value.toInt
      case d: QName if d.getNamespaceURI() == "urn:hl7-org:elm-types:r1" && d.getLocalPart() == "Boolean" => 
        value.toBoolean
      case d: QName if d.getNamespaceURI() == "urn:hl7-org:elm-types:r1" && d.getLocalPart() == "Long" =>
        value.toLong
      case d: QName if d.getNamespaceURI() == "urn:hl7-org:elm-types:r1" && d.getLocalPart() == "Decimal" => 
        value.toDouble
      case d: QName if d.getNamespaceURI() == "urn:hl7-org:elm-types:r1" && d.getLocalPart() == "String" => 
        value
    }
  }
}