package gov.va.sparkcql.core.translation.elm2spark

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Column}
import gov.va.sparkcql.core.model.elm.ElmTypes
import gov.va.sparkcql.core.model.elm.ElmTypes._

object ElmTypeConversion {

  def convert(elmType: ElmTypes.Any): Column = {
    elmType match {
      case DateInterval(low, high, lowClosed, highClosed) => 
        struct(lit(low.value).alias("low"), lit(high.value).alias("high"))
      case DateTimeInterval(low, high, lowClosed, highClosed) => 
        struct(lit(low.value).alias("low"), lit(high.value).alias("high"))
    }
  }
  
}
