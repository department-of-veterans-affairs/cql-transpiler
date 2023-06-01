package gov.va.sparkcql.dataprovider

trait Fetchable[T, K] {

  def isDefined(key: K): Boolean

  def fetchOne(key: K): Option[T]

  def fetchMany(keys: List[K]): List[T] = {
    keys.flatMap(fetchOne(_))
  }
}