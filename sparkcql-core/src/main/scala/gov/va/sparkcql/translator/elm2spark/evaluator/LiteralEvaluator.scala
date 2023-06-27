package gov.va.sparkcql.translator.elm2spark.evaluator

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

class LiteralEvaluator(val element: elm.Literal) extends Evaluator {

  override def evaluate(context: Context): Object = {
    val value = element.convertTo[String]
    lit(value)
  }
}