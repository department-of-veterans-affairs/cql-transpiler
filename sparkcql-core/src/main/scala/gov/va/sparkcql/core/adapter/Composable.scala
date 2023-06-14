package gov.va.sparkcql.core.adapter

import scala.collection.mutable.MutableList

trait Composable[T] {
  
  val adapters = MutableList[T]()

  def register(adapter: T): T = {
    adapters += adapter
    this.asInstanceOf[T]
  }

  def register(adapters: List[T]): T = {
    adapters.foreach(this.adapters += _)
    this.asInstanceOf[T]
  }

  def first[R](f: (T) => Option[R]): Option[R] = {
    adapters
    .view
    .flatMap(a => {
      f(a)
    }).headOption
  }

  def composeFirst[R](f: (T) => Option[R]): Option[R] = {
    adapters
      .view
      .flatMap(a => {
        f(a)
      }).headOption
  }  
}