package gov.va.sparkcql.translator.elm2spark.evaluator

import org.hl7.elm.{r1 => elm}

class NullEvaluator() extends Evaluator {

  val element: elm.Element = null

  override def evaluate(context: Context): Object = {
    null
  }
}