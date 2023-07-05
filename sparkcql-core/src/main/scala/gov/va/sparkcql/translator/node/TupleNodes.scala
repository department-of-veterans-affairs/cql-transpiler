package gov.va.sparkcql.translator.node

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
import gov.va.sparkcql.translator._

class TupleNode(val element: elm.Tuple) extends Node {

  override protected def resolveChildren(): List[Object] = element.getElement().asScala.toList

  override def translate(env: Environment): Object = {
    val cols = children.map(_.translate(env).asInstanceOf[Column])
    struct(cols:_*)
  }
}

class TupleElementNode(val element: elm.TupleElement) extends Node {

  override protected def resolveChildren(): List[Object] = List(element.getValue())

  override def translate(env: Environment): Object = {
    assert(children.length == 1)
    val value = children.head.translate(env).asInstanceOf[Column]
    value.alias(element.getName())
  }
}