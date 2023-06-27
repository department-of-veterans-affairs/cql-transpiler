package gov.va.sparkcql.translator.elm2spark.evaluator

import scala.reflect.runtime.universe._
import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.converter.Conversion

// trait EvaluationState
// case object NotStarted extends EvaluationState
// case object InProgress extends EvaluationState
// case object Complete extends EvaluationState

trait Evaluator {
  var parent: Evaluator = null
  val element: Object
  // var evaluationCache: Object = null
  // var evaluationState: EvaluationState = NotStarted
  
  val children: List[Evaluator] = {
    val resolved = resolveChildren()
    resolved.map(childElm => {
      val childEvaluator = EvaluatorFactory.create(childElm)
      if (childEvaluator != null) {
        childEvaluator.parent = this
        childEvaluator
      } else null
    }).filter(_ != null)
  }

  def children[T: TypeTag](): List[Evaluator] = {
    val elementType = typeOf[T]
    val found = children.filter(c => c.element != null && c.element.getClass().getName() == elementType.toString())
    found
  }

  protected def resolveChildren(): List[Object] = List()

  /**
    * Produces a flattened list of descendants from the current node.
    */
  val descendants: List[Evaluator] = {
    children ++ children.flatMap(_.descendants)
  }

  /**
    * Produces a flattened list of ancestors from the current node.
    */
  def ancestors: List[Evaluator] = {
    if (parent != null) {
      List(parent) ++ parent.ancestors
    } else {
      List()
    }
  }

  def root(): Evaluator = {
    ancestors.filter(_.parent == null).head
  }

  def evaluate(context: Context): Object

  // def evaluation(): Object = {
  //   evaluationState match {
  //     case NotStarted => 
  //       evaluationState == InProgress
  //       evaluationCache = evaluate()
  //       evaluationCache
  //     case InProgress => throw new Exception("Circular reference detected")
  //     case Complete => return evaluationCache
  //   }
  // }
  
  def inferModelNamespaceUri(): String = {
    if (parent != null) {
      parent.inferModelNamespaceUri()
    } else {
      null
    }
  }

  /**
    * Syntactical sugar to add .convertTo[] to any ELM Element node to improve readability.
    * Alias for Conversion.convert[S, T](node)
    */
  implicit class ConvertExtension(val s: Object) {
    def convertTo[T: TypeTag](): T = {
      Conversion.convert[T](s)
    }
  }
}