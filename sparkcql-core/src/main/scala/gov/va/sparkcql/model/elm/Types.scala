package gov.va.sparkcql.model.elm

import gov.va.sparkcql.model

object ElmTypes {
  sealed trait Any

  case class Boolean(value: Boolean) extends Any
  case class Date(value: java.lang.String) extends Any
  case class Time(value: java.lang.String) extends Any
  case class DateTime(value: java.lang.String) extends Any

  case class Code(code: java.lang.String, display: Option[java.lang.String], system: Option[java.lang.String], version: Option[java.lang.String]) extends Any
  case class Concept(codes: Seq[Code], display:java.lang.String) extends Any
  case class Vocabulary(id: java.lang.String, version: Option[java.lang.String], name: Option[java.lang.String]) extends Any
  case class ValueSet(id: java.lang.String, version: Option[String], name: Option[java.lang.String], codesystem: Seq[CodeSystem]) extends Any
  case class CodeSystem(value: scala.Boolean) extends Any
  
  case class Integer(value: scala.Int) extends Any
  case class Long(value: scala.math.BigInt) extends Any
  case class Decimal(value: scala.math.BigDecimal) extends Any
  case class String(value: java.lang.String) extends Any

  case class Quantity(value: scala.math.BigDecimal, unit: java.lang.String) extends Any
  case class Ratio(numerator: Quantity, denominator: Quantity) extends Any
  
  case class Interval(lowClosed: scala.Boolean, highClosed: scala.Boolean) extends Any
  case class IntegerInterval(low: Integer, high: Integer, lowClosed: scala.Boolean, highClosed: scala.Boolean) extends Any
  case class DecimalInterval(low: Decimal, high: Decimal, lowClosed: scala.Boolean, highClosed: scala.Boolean) extends Any
  case class QuantityInterval(low: Quantity, high: Quantity, lowClosed: scala.Boolean, highClosed: scala.Boolean) extends Any
  case class DateInterval(low: Date, high: Date, lowClosed: scala.Boolean, highClosed: scala.Boolean) extends Any
  case class DateTimeInterval(low: Time, high: Time, lowClosed: scala.Boolean, highClosed: scala.Boolean) extends Any
  case class TimeInterval(low: scala.Int, high: scala.Int, lowClosed: scala.Boolean, highClosed: scala.Boolean) extends Any
}