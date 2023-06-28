package gov.va.sparkcql.converter

import scala.reflect.runtime.universe._
import gov.va.sparkcql.logging.Log

object Converter {

  def convert[T: TypeTag](source: Object): T = {    

    val targetType = typeOf[T]
    
    targetType.toString() match {
      case "String" => Primitives.toString(source).asInstanceOf[T]
      case "Boolean" => Primitives.toBoolean(source).asInstanceOf[T]
      case "Decimal" => Primitives.toDecimal(source).asInstanceOf[T]
      case "Integer" => Primitives.toInteger(source).asInstanceOf[T]
      case "Int" => Primitives.toInteger(source).asInstanceOf[T]
      case "Long" => Primitives.toLong(source).asInstanceOf[T]
      
      case "org.hl7.elm.r1.Date" => Dates.toElmDate(source).asInstanceOf[T]
      case "org.hl7.elm.r1.DateTime" => Dates.toElmDateTime(source).asInstanceOf[T]
      case "java.time.LocalDate" => Dates.toLocalDate(source).asInstanceOf[T]
      case "java.time.LocalDateTime" => Dates.toLocalDateTime(source).asInstanceOf[T]
      case "java.sql.Timestamp" => Dates.toTimestamp(source).asInstanceOf[T]
      case "java.time.toZonedDateTime" => Dates.toZonedDateTime(source).asInstanceOf[T]

      case _ => throw new Exception(s"Cannot convert type ${source.getClass().toString()} to ${targetType.toString()}")
    }
  }
}