package gov.va.sparkcql.translator.node

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.translator._
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

class ListNode(val element: elm.List) extends Node {

  override protected def resolveChildren(): List[Object] = element.getElement().asScala.toList

  override def translate(context: Context): Object = {
    import context.spark.implicits._
    val items = children.map(_.translate(context).asInstanceOf[Column])
    array(items:_*)
  }
}