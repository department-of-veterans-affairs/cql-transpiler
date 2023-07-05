package gov.va.sparkcql.converter

import scala.reflect.runtime.universe._
import gov.va.sparkcql.converter.Converter._
import gov.va.sparkcql.io.Log
import org.hl7.elm.{r1 => elm}
import javax.xml.namespace.QName

object Primitives {

  def toInteger(source: Object) = {
    source match {
      case s: elm.Literal => s.getValue().toInt
      case _ => throw new Exception(s"Cannot convert type ${source.getClass().toString()}")
    }
  }

  def toBoolean(source: Object) = {
    source match {
      case s: elm.Literal => s.getValue().toBoolean
      case _ => throw new Exception(s"Cannot convert type ${source.getClass().toString()}")
    }
  }

  def toLong(source: Object) = {
    source match {
      case s: elm.Literal => s.getValue().toLong
      case _ => throw new Exception(s"Cannot convert type ${source.getClass().toString()}")
    }
  }

  def toDecimal(source: Object) = {
    source match {
      case s: elm.Literal => s.getValue().toDouble
      case _ => throw new Exception(s"Cannot convert type ${source.getClass().toString()}")
    }
  }

  def toString(source: Object) = {
    source match {
      case s: elm.Literal => s.getValue().toString
      case _ => throw new Exception(s"Cannot convert type ${source.getClass().toString()}")
    }
  }
}