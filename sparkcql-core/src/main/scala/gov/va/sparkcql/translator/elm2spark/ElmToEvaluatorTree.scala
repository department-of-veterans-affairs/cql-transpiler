package gov.va.sparkcql.translator.elm2spark

import org.cqframework.cql.elm.visiting.ElmBaseLibraryVisitor
import org.hl7.elm.r1.Library
import gov.va.sparkcql.translator.elm2spark.evaluator._

class ElmToEvaluatorTree extends ElmBaseLibraryVisitor[Evaluator, Evaluator] {

  override protected def aggregateResult(aggregate: Evaluator, nextResult: Evaluator): Evaluator = {
    ???
  }

  override def visitLibrary(elm: Library, context: Evaluator): Evaluator = {
    ???
    // LibraryEvaluator(context, elm)
  }
}