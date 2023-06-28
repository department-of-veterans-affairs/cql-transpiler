package gov.va.sparkcql.evaluator.node

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.evaluator._

class PackageNode(val element: List[elm.Library]) extends Node {

  override protected def resolveChildren(): List[Object] = element
  
  override def evaluate(context: Context): Object = {
    val libraryEvals = children.map(l => {
      l.evaluate(context).asInstanceOf[LibraryResult]
    })
    EvaluationResult(context.parameters, libraryEvals)
  }
}