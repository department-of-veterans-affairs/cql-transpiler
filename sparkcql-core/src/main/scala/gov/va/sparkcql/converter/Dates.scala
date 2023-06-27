package gov.va.sparkcql.converter

import scala.reflect.runtime.universe._
import gov.va.sparkcql.converter.Converter._
import gov.va.sparkcql.logging.Log
import org.hl7.elm
import java.time.{ZonedDateTime, LocalDate, LocalDateTime}
import javax.xml.namespace.QName
import java.time.ZoneId
import java.sql.Timestamp

trait DateConverter {

  val NanoToMillFactor = 1000000

  implicit val zonedDateTimeToDateTime: Convertable[ZonedDateTime, elm.r1.DateTime] = (s: ZonedDateTime) => {
    new elm.r1.DateTime()
      .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
      .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
      .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
      .withHour(new elm.r1.Literal().withValue(s.getHour().toString()))
      .withMinute(new elm.r1.Literal().withValue(s.getMinute().toString()))
      .withSecond(new elm.r1.Literal().withValue(s.getSecond().toString()))
      .withMillisecond(new elm.r1.Literal().withValue((s.getNano() / NanoToMillFactor).toString()))
      // TODO: TimeZoneOffset
  }

  implicit val zonedDateTimeToDate: Convertable[ZonedDateTime, elm.r1.Date] = (s: ZonedDateTime) => {
    Log.warn(s"Downcasting ZonedDateTime to Date might result in a loss of information.")
    new elm.r1.Date()
      .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
      .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
      .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
  }  

  implicit val localDateToDate: Convertable[LocalDate, elm.r1.Date] = (s: LocalDate) => {
    new elm.r1.Date()
      .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
      .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
      .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
  }

  implicit val localDateTimeToDateTime: Convertable[LocalDateTime, elm.r1.DateTime] = (s: LocalDateTime) => {
    new elm.r1.DateTime()
      .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
      .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
      .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
      .withHour(new elm.r1.Literal().withValue(s.getHour().toString()))
      .withMinute(new elm.r1.Literal().withValue(s.getMinute().toString()))
      .withSecond(new elm.r1.Literal().withValue(s.getSecond().toString()))
      .withMillisecond(new elm.r1.Literal().withValue((s.getNano() / NanoToMillFactor).toString()))
  }

  implicit val localDateTimeToDate: Convertable[LocalDateTime, elm.r1.Date] = (s: LocalDateTime) => {
    Log.warn(s"Downcasting LocalDateTime to Date might result in a loss of information.")
    new elm.r1.Date()
      .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
      .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
      .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
  }  

  implicit val localDateToDateTime: Convertable[LocalDate, elm.r1.DateTime] = (s: LocalDate) => {
    Log.debug(s"Upcasting LocalDate to DateTime using midnight as time default.")
    new elm.r1.DateTime()
      .withYear(new elm.r1.Literal().withValue(s.getYear().toString()))
      .withMonth(new elm.r1.Literal().withValue(s.getMonthValue().toString()))
      .withDay(new elm.r1.Literal().withValue(s.getDayOfMonth().toString()))
  }

  implicit val dateTimeToZonedDateTime: Convertable[elm.r1.DateTime, ZonedDateTime] = (s: elm.r1.DateTime) => {
    Log.warn(s"Upcasting DateTime to ZonedDateTime using system default timezone.")
    ZonedDateTime.of(
      convert[elm.r1.Literal, Int](s.getYear().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](s.getDay().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](literalOrElse(s.getHour(), "0")),
      convert[elm.r1.Literal, Int](literalOrElse(s.getMinute(), "0")),
      convert[elm.r1.Literal, Int](literalOrElse(s.getSecond(), "0")), 
      convert[elm.r1.Literal, Int](literalOrElse(s.getMillisecond(), "0")) * NanoToMillFactor,
      ZoneId.systemDefault())
  }

  implicit val dateTimeToLocalDateTime: Convertable[elm.r1.DateTime, LocalDateTime] = (s: elm.r1.DateTime) => {
    LocalDateTime.of(
      convert[elm.r1.Literal, Int](s.getYear().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](s.getDay().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](literalOrElse(s.getHour(), "0")),
      convert[elm.r1.Literal, Int](literalOrElse(s.getMinute(), "0")),
      convert[elm.r1.Literal, Int](literalOrElse(s.getSecond(), "0")), 
      convert[elm.r1.Literal, Int](literalOrElse(s.getMillisecond(), "0")) * NanoToMillFactor)
  }

  implicit val dateTimeToLocalDate: Convertable[elm.r1.DateTime, LocalDate] = (s: elm.r1.DateTime) => {
    Log.warn(s"Downcasting DateTime to LocalDate might result in a loss of information.")
    LocalDate.of(
      convert[elm.r1.Literal, Int](s.getYear().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](s.getDay().asInstanceOf[elm.r1.Literal]))
  }  

  implicit val dateToLocalDate: Convertable[elm.r1.Date, LocalDate] = (s: elm.r1.Date) => {
    LocalDate.of(
      convert[elm.r1.Literal, Int](s.getYear().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
      convert[elm.r1.Literal, Int](s.getDay().asInstanceOf[elm.r1.Literal]))
  }

  implicit val localDateTimeToTimestamp: Convertable[LocalDateTime, Timestamp] = (s: LocalDateTime) => {
    java.sql.Timestamp.valueOf(s)
  }

  implicit val dateTimeToTimestamp: Convertable[elm.r1.DateTime, Timestamp] = (s: elm.r1.DateTime) => {
    val toLocalDateTime = convert[elm.r1.DateTime, LocalDateTime](s)
    convert[LocalDateTime, Timestamp](toLocalDateTime)
  }

  protected def literalOrElse(e: elm.r1.Element, default: String): elm.r1.Literal = {
    if (e != null) {
      e.asInstanceOf[elm.r1.Literal]
    } else {
      new elm.r1.Literal().withValue(default).asInstanceOf[elm.r1.Literal]
    }
  }

  implicit val stringToDateTime: Convertable[String, elm.r1.DateTime] = (s: String) => {
    if (s.contains("+")) {
      convert[ZonedDateTime, elm.r1.DateTime](ZonedDateTime.parse(s))
    } else if (s.contains("T")) {
      convert[LocalDateTime, elm.r1.DateTime](LocalDateTime.parse(s))
    } else {
      convert[LocalDate, elm.r1.DateTime](LocalDate.parse(s))
    }
  }

  implicit val stringToDate: Convertable[String, elm.r1.Date] = (s: String) => {
    if (s.contains("+")) {
      convert[ZonedDateTime, elm.r1.Date](ZonedDateTime.parse(s))
    } else if (s.contains("T")) {
      convert[LocalDateTime, elm.r1.Date](LocalDateTime.parse(s))
    } else {
      convert[LocalDate, elm.r1.Date](LocalDate.parse(s))
    }
  }
}

object Dates {

  val NanoToMillFactor = 1000000

  def toElmDateTime(source: Object) = {
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
          convert[ZonedDateTime, elm.r1.DateTime](ZonedDateTime.parse(s))
        } else if (s.contains("T")) {
          convert[LocalDateTime, elm.r1.DateTime](LocalDateTime.parse(s))
        } else {
          convert[LocalDate, elm.r1.DateTime](LocalDate.parse(s))
        }
    }
  }

  def toElmDate(source: Object) = {
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
          convert[ZonedDateTime, elm.r1.Date](ZonedDateTime.parse(s))
        } else if (s.contains("T")) {
          convert[LocalDateTime, elm.r1.Date](LocalDateTime.parse(s))
        } else {
          convert[LocalDate, elm.r1.Date](LocalDate.parse(s))
        }
    }
  }  

  def toZonedDateTime(source: Object) = {
    source match {
      case s: elm.r1.DateTime =>
        Log.warn(s"Upcasting DateTime to ZonedDateTime using system default timezone.")
        ZonedDateTime.of(
          convert[elm.r1.Literal, Int](s.getYear().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](s.getDay().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](literalOrElse(s.getHour(), "0")),
          convert[elm.r1.Literal, Int](literalOrElse(s.getMinute(), "0")),
          convert[elm.r1.Literal, Int](literalOrElse(s.getSecond(), "0")), 
          convert[elm.r1.Literal, Int](literalOrElse(s.getMillisecond(), "0")) * NanoToMillFactor,
          ZoneId.systemDefault())
    }
  }

  def toLocalDateTime(source: Object) = {
    source match {
      case s: elm.r1.DateTime => 
        LocalDateTime.of(
          convert[elm.r1.Literal, Int](s.getYear().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](s.getDay().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](literalOrElse(s.getHour(), "0")),
          convert[elm.r1.Literal, Int](literalOrElse(s.getMinute(), "0")),
          convert[elm.r1.Literal, Int](literalOrElse(s.getSecond(), "0")), 
          convert[elm.r1.Literal, Int](literalOrElse(s.getMillisecond(), "0")) * NanoToMillFactor)
    }
  }

  def toLocalDate(source: Object) = {
    source match {
      case s: elm.r1.DateTime =>
        Log.warn(s"Downcasting DateTime to LocalDate might result in a loss of information.")
        LocalDate.of(
          convert[elm.r1.Literal, Int](s.getYear().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](s.getDay().asInstanceOf[elm.r1.Literal]))
      case s: elm.r1.Date =>
        LocalDate.of(
          convert[elm.r1.Literal, Int](s.getYear().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](s.getMonth().asInstanceOf[elm.r1.Literal]),
          convert[elm.r1.Literal, Int](s.getDay().asInstanceOf[elm.r1.Literal]))
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