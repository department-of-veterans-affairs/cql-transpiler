package gov.va.sparkcql.model

import scala.reflect.runtime.universe._
import scala.collection.JavaConverters._
import javax.xml.namespace.QName
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.logging.Log

trait Model {

  val namespaceUri: String

  val supportedDataTypes: List[QName]

  def schemaOf(dataType: QName): Option[StructType]
  
  def toDataType(localName: String): QName = {
    new QName(namespaceUri, localName)
  }

  def toDataType(localName: String, version: String): QName = {
    Log.error(s"TODO: Make sure this is correct: ${new QName(namespaceUri, localName, version).toString()}")
    new QName(namespaceUri, localName, version)
  }

  def metaInterval(typeName: String): (String, String)
}

object Model {
  
  def toDataType[T : TypeTag](): QName = {
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