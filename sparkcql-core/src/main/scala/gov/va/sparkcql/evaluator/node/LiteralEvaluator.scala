package gov.va.sparkcql.evaluator.node

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.evaluator._
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

class LiteralNode(val element: elm.Literal) extends Node {

  override def evaluate(context: Context): Object = {
    val value = element.convertTo[String]
    lit(value)
  }
}