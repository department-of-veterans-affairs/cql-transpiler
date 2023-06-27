package gov.va.sparkcql.converter

import scala.reflect.runtime.universe._
import gov.va.sparkcql.logging.Log

object Conversion extends ElmConverter with DateConverter {

  def convert[T: TypeTag](s: Object): T = {    
    val targetType = typeOf[T]
    
    targetType.toString() match {
      case "String" => Primitives.toLiteralString(s).asInstanceOf[T]
      case "Boolean" => Primitives.toLiteralBoolean(s).asInstanceOf[T]
      case "Decimal" => Primitives.toLiteralDecimal(s).asInstanceOf[T]
      case "Integer" => Primitives.toLiteralInteger(s).asInstanceOf[T]
      case "Long" => Primitives.toLiteralLong(s).asInstanceOf[T]
      
      case "org.hl7.elm.r1.Date" => Dates.toElmDate(s).asInstanceOf[T]
      case "org.hl7.elm.r1.DateTime" => Dates.toElmDateTime(s).asInstanceOf[T]
      case "java.time.LocalDate" => Dates.toLocalDate(s).asInstanceOf[T]
      case "java.time.LocalDateTime" => Dates.toLocalDateTime(s).asInstanceOf[T]
      case "java.sql.Timestamp" => Dates.toTimestamp(s).asInstanceOf[T]
      case "java.time.toZonedDateTime" => Dates.toZonedDateTime(s).asInstanceOf[T]
    }
  }
}