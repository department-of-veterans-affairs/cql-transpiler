package gov.va.sparkcql.translator.elm2spark

import scala.reflect.runtime.universe._

private class ContextStack(enclosing: Option[ContextStack], newState: List[ContextType]) {

  def this() = this(None, List())

  val state: List[ContextType] = {
    if (enclosing.isDefined) {
      enclosing.get.state ++ newState
    }
    else {
      List()
    }
  }
  
  def last[T: TypeTag](): T = all[T].last

  def first[T: TypeTag](): T = all[T].head

  def all[T: TypeTag](): Seq[T] = {
    val searchType = typeOf[T]
    val found = state.filter(f => f.getClass().getName() == searchType.toString())
    found.map(_.asInstanceOf[T]).toSeq
  }

  def push(item: ContextType): ContextStack = {
    ContextStack(this, List(item))
  }

  def push(item: List[ContextType]): ContextStack = {
    ContextStack(this, item)
  }
}

private object ContextStack {
  def apply() = new ContextStack()
  def apply(enclosing: ContextStack) = new ContextStack(Some(enclosing), List())
  def apply(enclosing: ContextStack, newState: List[ContextType]) = new ContextStack(Some(enclosing), newState)
}