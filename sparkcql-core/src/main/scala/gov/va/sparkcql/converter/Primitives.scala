package gov.va.sparkcql.converter

import scala.reflect.runtime.universe._
import gov.va.sparkcql.converter.Converter._
import gov.va.sparkcql.logging.Log
import org.hl7.elm.{r1 => elm}
import javax.xml.namespace.QName

trait ElmConverter {

  implicit val literalToInt: Convertable[elm.Literal, Int] = (s: elm.Literal) => s.getValue().toInt

  implicit val literalToBoolean: Convertable[elm.Literal, Boolean] = (s: elm.Literal) => s.getValue().toBoolean
  
  implicit val literalToLong: Convertable[elm.Literal, Long] = (s: elm.Literal) => s.getValue().toLong

  implicit val literalToDouble: Convertable[elm.Literal, Double] = (s: elm.Literal) => s.getValue().toDouble

  implicit val literalToString: Convertable[elm.Literal, String] = (s: elm.Literal) => s.getValue()

  implicit val literalToScala: Convertable[elm.Literal, Any] = (s: elm.Literal) => {
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

object Primitives {

  def toLiteralInteger(source: Object) = source.asInstanceOf[elm.Literal].getValue().toInt

  def toLiteralBoolean(source: Object) = source.asInstanceOf[elm.Literal].getValue().toBoolean

  def toLiteralLong(source: Object) = source.asInstanceOf[elm.Literal].getValue().toLong

  def toLiteralDecimal(source: Object) = source.asInstanceOf[elm.Literal].getValue().toDouble

  def toLiteralString(source: Object) = source.asInstanceOf[elm.Literal].getValue().toString()
}