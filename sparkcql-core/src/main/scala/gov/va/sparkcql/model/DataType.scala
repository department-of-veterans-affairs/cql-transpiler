package gov.va.sparkcql.model

import scala.reflect.runtime.universe._
import javax.xml.namespace.QName

object DataType {
    
  def apply(namespaceUri: String, localName: String): QName = new QName(namespaceUri, localName)

  def apply(namespaceUri: String, localName: String, version: String): QName = new QName(namespaceUri, localName, version)

  def apply[T : TypeTag](): QName = {
    val cls = typeOf[T]
    var tokens = cls.toString().split('.')
    assert(tokens.length > 2)
    var baseUrl = s"http://${tokens(1)}.${tokens(0)}"
    var dirs = tokens.slice(2, tokens.length - 1)
    val system = Array(baseUrl, dirs.mkString("/")).mkString("/")
    val name = tokens.last

    new QName(system, name)
  }
}