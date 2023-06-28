package gov.va.sparkcql.evaluator.node

import org.hl7.elm.{r1 => elm}
import org.apache.spark.sql.{DataFrame, Dataset, Row}
import gov.va.sparkcql.evaluator._
import org.apache.spark.sql.Column

class ExpressionDefNode(val element: elm.ExpressionDef) extends Node {

  override protected def resolveChildren(): List[Object] = List(element.getExpression)

  override def evaluate(context: Context): Object = {
    assert(children.length == 1)
    val eval = children.head.evaluate(context)
    if (eval.getClass() == classOf[DataFrame]) {
      ExpressionDefResult(element, eval.asInstanceOf[DataFrame])
    } else {
      import context.spark.implicits._
      val c = eval.asInstanceOf[Column]
      val df = context.spark.createDataFrame(List())
      df.withColumn("value", eval.asInstanceOf[Column])
    }
  }
}