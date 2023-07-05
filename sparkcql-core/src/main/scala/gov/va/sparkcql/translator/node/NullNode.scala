package gov.va.sparkcql.translator.node

import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.translator._

class NullNode() extends Node {

  val element: elm.Element = null

  override def translate(env: Environment): Object = {
    null
  }
}