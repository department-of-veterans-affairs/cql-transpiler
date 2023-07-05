package gov.va.sparkcql.translator.node

import scala.reflect.runtime.universe._
import org.hl7.elm.{r1 => elm}
import gov.va.sparkcql.converter.Converter
import gov.va.sparkcql.translator._

// trait EvaluationState
// case object NotStarted extends EvaluationState
// case object InProgress extends EvaluationState
// case object Complete extends EvaluationState

trait Node {
  var parent: Node = null
  val element: Object
  // var evaluationCache: Object = null
  // var evaluationState: EvaluationState = NotStarted
  
  val children: List[Node]  = {
    val resolved = resolveChildren()
    resolved.map(childElm => {
      val childEvaluator = NodeFactory.create(childElm)
      if (childEvaluator != null) {
        childEvaluator.parent = this
        childEvaluator
      } else null
    }).filter(_ != null)
  }

  def children[T: TypeTag](): List[Node]  = {
    val elementType = typeOf[T]
    val found = children.filter(c => c.element != null && c.element.getClass().getName() == elementType.toString())
    found
  }

  protected def resolveChildren(): List[Object] = List()

  /**
    * Produces a flattened list of descendants from the current node.
    */
  val descendants: List[Node]  = {
    children ++ children.flatMap(_.descendants)
  }

  /**
    * Produces a flattened list of ancestors from the current node.
    */
  def ancestors: List[Node]  = {
    if (parent != null) {
      List(parent) ++ parent.ancestors
    } else {
      List()
    }
  }

  def root(): Node = {
    ancestors.filter(_.parent == null).head
  }

  def translate(context: Context): Object

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
      Converter.convert[T](s)
    }
  }
}