package gov.va.sparkcql.session

import gov.va.sparkcql.converter.Converter
import org.hl7.elm.r1.{Interval, DateTime, Date, Literal}
import java.time.{LocalDate, LocalDateTime, ZonedDateTime}

private[session] class ParameterBuilder(name: String) {
  
  def dateTimeInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, Object] = {
    val lowDateTime = Converter.convert[DateTime](low)
    val highDateTime = Converter.convert[DateTime](high)

    val interval = new Interval()
      .withLow(lowDateTime)
      .withLowClosed(lowClosed)
      .withHigh(highDateTime)
      .withHighClosed(highClosed)

    Map(name -> interval)
  }

  def dateInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, Object] = {
    val lowDate = Converter.convert[Date](low)
    val highDate = Converter.convert[Date](high)

    val interval = new Interval()
      .withLow(lowDate)
      .withLowClosed(lowClosed)
      .withHigh(highDate)
      .withHighClosed(highClosed)

    Map(name -> interval)
  }
}