/**
  * TODO: Provide thorough explanation of our use of the typeclass pattern to resolve
  * conversion implementations.
  */
package gov.va.sparkcql.converter

// TODO: Consider rewriting to avoid implicits/typeclasses altogether: https://gist.github.com/jkpl/5279ee05cca8cc1ec452fc26ace5b68b

trait Convertable[S, T] {
  def convert(source: S): T
}

object Converter extends ElmConverter with DateConverter {

  /**
    * NOTE: We could have explicitly routed each request of source/target to correct implementation
    * but typeclasses via implicits does this for us.
    *
    * @param source
    * @param converter
    * @return
    */
  def convert[S, T](s: S)(implicit converter: Convertable[S, T]) = converter.convert(s)
}