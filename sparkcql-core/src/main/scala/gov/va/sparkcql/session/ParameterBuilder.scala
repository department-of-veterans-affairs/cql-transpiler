package gov.va.sparkcql.session

import gov.va.sparkcql.converter.Converter._
import org.hl7.elm.r1.{Interval, DateTime, Date, Literal}
import java.time.{LocalDate, LocalDateTime, ZonedDateTime}

private[session] class ParameterBuilder(name: String) {
  
  def dateTimeInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, Object] = {
    val lowDateTime = convert[String, DateTime](low)
    val highDateTime = convert[String, DateTime](high)

    val interval = new Interval()
      .withLow(lowDateTime)
      .withLowClosed(lowClosed)
      .withHigh(highDateTime)
      .withHighClosed(highClosed)

    Map(name -> interval)
  }

  def dateInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, Object] = {
    val lowDate = convert[String, Date](low)
    val highDate = convert[String, Date](high)

    val interval = new Interval()
      .withLow(lowDate)
      .withLowClosed(lowClosed)
      .withHigh(highDate)
      .withHighClosed(highClosed)

    Map(name -> interval)
  }
}