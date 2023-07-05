package gov.va.sparkcql.translator.node

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import java.time.LocalDate
import gov.va.sparkcql.translator._

class DateNode(val element: elm.Date) extends Node {

  override def translate(env: Environment): Object = {
    val value = element.convertTo[LocalDate]
    lit(value)
  }
}