package gov.va.sparkcql.converter

import scala.reflect.runtime.universe._
import gov.va.sparkcql.logging.Log
import org.hl7.elm
import java.time.{ZonedDateTime, LocalDate, LocalDateTime}
import javax.xml.namespace.QName
import java.time.ZoneId
import java.sql.Timestamp

object Dates {

  val NanoToMillFactor = 1000000

  def toElmDateTime(source: Object): elm.r1.DateTime = {
    source match {
      case s: ZonedDateTime => 
        new elm.r1.DateTime()
          .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
          .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
          .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
          .withHour(new elm.r1.Literal().withValue(s.getHour().toString()))
          .withMinute(new elm.r1.Literal().withValue(s.getMinute().toString()))
          .withSecond(new elm.r1.Literal().withValue(s.getSecond().toString()))
          .withMillisecond(new elm.r1.Literal().withValue((s.getNano() / NanoToMillFactor).toString()))
      case s: LocalDateTime => 
        new elm.r1.DateTime()
          .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
          .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
          .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
          .withHour(new elm.r1.Literal().withValue(s.getHour().toString()))
          .withMinute(new elm.r1.Literal().withValue(s.getMinute().toString()))
          .withSecond(new elm.r1.Literal().withValue(s.getSecond().toString()))
          .withMillisecond(new elm.r1.Literal().withValue((s.getNano() / NanoToMillFactor).toString()))
      // TODO: TimeZoneOffset
      case s: LocalDate =>
        Log.debug(s"Upcasting LocalDate to DateTime using midnight as time default.")
        new elm.r1.DateTime()
          .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
          .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
          .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
      case s: String =>
        if (s.contains("+")) {
          Dates.toElmDateTime(ZonedDateTime.parse(s))
        } else if (s.contains("T")) {
          Dates.toElmDateTime(LocalDateTime.parse(s))
        } else {
          Dates.toElmDateTime(LocalDate.parse(s))
        }
    }
  }

  def toElmDate(source: Object): elm.r1.Date = {
    source match {
      case s: ZonedDateTime =>
        Log.warn(s"Downcasting ZonedDateTime to Date might result in a loss of information.")
        new elm.r1.Date()
          .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
          .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
          .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
      case s: LocalDate =>
        new elm.r1.Date()
          .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
          .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
          .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
      case s: LocalDateTime =>
        Log.warn(s"Downcasting LocalDateTime to Date might result in a loss of information.")
        new elm.r1.Date()
          .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
          .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
          .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
      case s: String =>
        if (s.contains("+")) {
          Dates.toElmDate(ZonedDateTime.parse(s))
        } else if (s.contains("T")) {
          Dates.toElmDate(LocalDateTime.parse(s))
        } else {
          Dates.toElmDate(LocalDate.parse(s))
        }
    }
  }  

  def toZonedDateTime(source: Object): ZonedDateTime = {
    source match {
      case s: elm.r1.DateTime =>
        Log.warn(s"Upcasting DateTime to ZonedDateTime using system default timezone.")
        ZonedDateTime.of(
          Converter.convert[Int](s.getYear().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](s.getDay().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](literalOrElse(s.getHour(), "0")),
          Converter.convert[Int](literalOrElse(s.getMinute(), "0")),
          Converter.convert[Int](literalOrElse(s.getSecond(), "0")), 
          Converter.convert[Int](literalOrElse(s.getMillisecond(), "0")) * NanoToMillFactor,
          ZoneId.systemDefault())
    }
  }

  def toLocalDateTime(source: Object): LocalDateTime = {
    source match {
      case s: elm.r1.DateTime => 
        LocalDateTime.of(
          Converter.convert[Int](s.getYear().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](s.getDay().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](literalOrElse(s.getHour(), "0")),
          Converter.convert[Int](literalOrElse(s.getMinute(), "0")),
          Converter.convert[Int](literalOrElse(s.getSecond(), "0")), 
          Converter.convert[Int](literalOrElse(s.getMillisecond(), "0")) * NanoToMillFactor)
    }
  }

  def toLocalDate(source: Object): LocalDate = {
    source match {
      case s: elm.r1.DateTime =>
        Log.warn(s"Downcasting DateTime to LocalDate might result in a loss of information.")
        LocalDate.of(
          Converter.convert[Int](s.getYear().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](s.getDay().asInstanceOf[elm.r1.Literal]))
      case s: elm.r1.Date =>
        LocalDate.of(
          Converter.convert[Int](s.getYear().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
          Converter.convert[Int](s.getDay().asInstanceOf[elm.r1.Literal]))
    }
  }

  def toTimestamp(source: Object): Object = {
    source match {
      case s: LocalDateTime => java.sql.Timestamp.valueOf(s)
      case s: elm.r1.DateTime => 
        val toLocalDateTime = this.toLocalDateTime(s)
        this.toTimestamp(toLocalDateTime)
    }
  }

  protected def literalOrElse(e: elm.r1.Element, default: String): elm.r1.Literal = {
    if (e != null) {
      e.asInstanceOf[elm.r1.Literal]
    } else {
      new elm.r1.Literal().withValue(default).asInstanceOf[elm.r1.Literal]
    }
  }
}