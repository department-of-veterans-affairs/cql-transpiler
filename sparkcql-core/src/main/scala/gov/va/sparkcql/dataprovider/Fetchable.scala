package gov.va.sparkcql.dataprovider

trait Fetchable[K, V] {

  def isDefined(key: K): Boolean

  def fetch(key: K): Option[V]
  
  def fetchOrElse[V1 >: V](key: K, default: => V1): V1 = {
    fetch(key).getOrElse(default)
  }
}