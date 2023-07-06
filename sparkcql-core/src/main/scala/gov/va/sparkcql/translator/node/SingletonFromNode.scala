package gov.va.sparkcql.translator.node

import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.translator._
import org.apache.spark.sql.{DataFrame, Column}
import org.apache.spark.sql.functions._

class SingletonFromNode(val element: elm.SingletonFrom) extends Node {

  override def translate(env: Environment): Object = {
    val results = children.map(_.translate(env))
    assert(results.length == 1)
    results.head.asInstanceOf[DataFrame]
  }
}