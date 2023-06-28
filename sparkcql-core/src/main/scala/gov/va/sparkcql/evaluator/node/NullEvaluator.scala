package gov.va.sparkcql.evaluator.node

import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.evaluator._

class NullNode() extends Node {

  val element: elm.Element = null

  override def evaluate(context: Context): Object = {
    null
  }
}