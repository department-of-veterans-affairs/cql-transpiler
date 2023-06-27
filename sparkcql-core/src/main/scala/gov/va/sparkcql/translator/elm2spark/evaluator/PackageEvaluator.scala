package gov.va.sparkcql.translator.elm2spark.evaluator

import scala.collection.JavaConverters._
import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.translator.elm2spark.{LibraryTranslation, Translation}

class PackageEvaluator(val element: List[elm.Library]) extends Evaluator {

  override protected def resolveChildren(): List[Object] = element
  
  override def evaluate(context: Context): Object = {
    val libraryEvals = children.map(l => {
      l.evaluate(context).asInstanceOf[LibraryTranslation]
    })
    Translation(context.parameters, libraryEvals)
  }
}