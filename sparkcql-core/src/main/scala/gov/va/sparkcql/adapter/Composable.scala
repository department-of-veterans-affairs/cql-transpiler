package gov.va.sparkcql.adapter

import scala.collection.mutable.MutableList

trait Composable[T] {
  
  val adapters = MutableList[T]()

  def register(adapter: T): Unit = {
    adapters += adapter
  }

  def first[R](f: (T) => Option[R]): Option[R] = {
    adapters
    .view
    .flatMap(a => {
      f(a)
    }).headOption
  }
}