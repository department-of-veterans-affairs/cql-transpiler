package gov.va.sparkcql.evaluator.node

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.evaluator._
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

class ListNode(val element: elm.List) extends Node {

  override protected def resolveChildren(): List[Object] = element.getElement().asScala.toList

  override def evaluate(context: Context): Object = {
    import context.spark.implicits._
    val items = children.map(_.evaluate(context).asInstanceOf[Column])
    array(items:_*)
  }
}