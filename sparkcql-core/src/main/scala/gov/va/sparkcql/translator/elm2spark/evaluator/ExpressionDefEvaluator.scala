package gov.va.sparkcql.translator.elm2spark.evaluator

import org.hl7.elm.{r1 => elm}
import org.apache.spark.sql.{DataFrame, Dataset, Row}
import gov.va.sparkcql.translator.elm2spark.ExpressionDefTranslation
import org.apache.spark.sql.Column

class ExpressionDefEvaluator(val element: elm.ExpressionDef) extends Evaluator {

  override protected def resolveChildren(): List[Object] = List(element.getExpression)

  override def evaluate(context: Context): Object = {
    assert(children.length == 1)
    val eval = children.head.evaluate(context)
    if (eval.isInstanceOf[DataFrame]) {
      ExpressionDefTranslation(element, eval.asInstanceOf[DataFrame])
    } else {
      import context.spark.implicits._
      val c = eval.asInstanceOf[Column]
      val df = context.spark.createDataFrame(List())
      df.withColumn("value", eval.asInstanceOf[Column])
    }
  }
}