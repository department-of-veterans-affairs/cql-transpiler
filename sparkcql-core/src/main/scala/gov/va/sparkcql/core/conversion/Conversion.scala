/**
  * TODO: Provide thorough explanation of our use of the typeclass pattern to resolve
  * conversion implementations.
  */
package gov.va.sparkcql.core.conversion

trait Convertable[S, T] {
  def convert(source: S): T
}

trait ConvertTo[T] {
}

object Conversion extends ElmConversion with NativeConversion with DateConversion {

  /**
    * NOTE: We could have explicitly routed each request of source/target to correct implementation
    * but typeclasses via implicits does this for us.
    *
    * @param source
    * @param converter
    * @return
    */
  def convert[S, T](s: S)(implicit converter: Convertable[S, T]) = converter.convert(s)

  /**
    * Syntactical sugar to add .convertTo[] to any ELM Element node to improve readability.
    * Alias for Conversion.convert[S, T](node)
    */
  implicit class ConvertExtension[S](val s: S) extends AnyVal {
    def convertTo[T](implicit evidence: Convertable[S, T]): T = {
      Conversion.convert[S, T](s)
    }
  }

  /**
    * Syntactical sugar to add .castTo[] to any ELM Element node to improve readability.
    * Alias for node.asInstanceOf[]
    */
  implicit class CastToExtension[T](val node: T) extends AnyVal {
    def castTo[T]: T = {
      node.asInstanceOf[T]
    }
  }  
}