package gov.va.sparkcql.core.model

import scala.reflect.runtime.universe._
import scala.collection.JavaConverters._
import javax.xml.namespace.QName
import org.apache.spark.sql.types.StructType

trait Model {

  val namespaceUri: String

  val supportedDataTypes: List[QName]

  def makeLocalDataType(localName: String): QName = {
    new QName(namespaceUri, localName)
  }

  def schemaOf(dataType: QName): Option[StructType]
}