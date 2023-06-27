package gov.va.sparkcql.translator.elm2spark.evaluator

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

class ListEvaluator(val element: elm.List) extends Evaluator {

  override protected def resolveChildren(): List[Object] = element.getElement().asScala.toList

  override def evaluate(context: Context): Object = {
    import context.spark.implicits._
    val items = children.map(_.evaluate(context).asInstanceOf[Column])
    array(items:_*)
  }
}