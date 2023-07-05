package gov.va.sparkcql.translator.node

import org.hl7.elm.{r1 => elm}
import org.apache.spark.sql.{DataFrame, Dataset, Row}
import gov.va.sparkcql.translator._
import org.apache.spark.sql.Column

class ExpressionDefNode(val element: elm.ExpressionDef) extends Node {

  override protected def resolveChildren(): List[Object] = List(element.getExpression)

  override def translate(env: Environment): Object = {
    assert(children.length == 1)
    val eval = children.head.translate(env)
    if (eval.getClass() == classOf[DataFrame]) {
      ExpressionDefTranslation(element, eval.asInstanceOf[DataFrame])
    } else {
      import env.spark.implicits._
      val c = eval.asInstanceOf[Column]
      val df = env.spark.createDataFrame(List())
      df.withColumn("value", eval.asInstanceOf[Column])
    }
  }
}