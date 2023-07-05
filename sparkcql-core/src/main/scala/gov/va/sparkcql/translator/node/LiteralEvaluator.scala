package gov.va.sparkcql.translator.node

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.translator._
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

class LiteralNode(val element: elm.Literal) extends Node {

  override def translate(context: Context): Object = {
    val value = element.convertTo[String]
    lit(value)
  }
}