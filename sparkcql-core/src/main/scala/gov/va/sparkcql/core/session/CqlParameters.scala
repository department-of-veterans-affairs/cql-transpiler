package gov.va.sparkcql.core.session

import gov.va.sparkcql.core.model.elm.ElmTypes
import java.time.{LocalDate, ZonedDateTime}

private[session] class Parameter(name: String) {
  
  def dateInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, ElmTypes.Any] = {
    Map(name -> ElmTypes.DateInterval(
      ElmTypes.Date(low),
      ElmTypes.Date(high),
      lowClosed, highClosed))
  }

  def dateTimeInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, ElmTypes.Any] = {
    Map(name -> ElmTypes.DateTimeInterval(
      ElmTypes.DateTime(low),
      ElmTypes.DateTime(high),
      lowClosed, highClosed))
  }
}