package gov.va.sparkcql.core.adapter.model

import scala.reflect.runtime.universe._
import scala.collection.JavaConverters._
import javax.xml.namespace.QName
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.core.adapter.Adapter

case class ModelCapabilityStatement(cqlModelSupport: Boolean, valueSetModelSupport: Boolean, supportedDataTypes: Option[List[QName]])

trait ModelAdapter extends Adapter {

  val NoneCapabilityStatement = ModelCapabilityStatement(false, false, None)

  def namespaceUri(): String

  def makeLocalDataType(localName: String): QName = {
    new QName(namespaceUri(), localName)
  }

  def stateCapabilities(): ModelCapabilityStatement

  def schemaOf(dataType: QName): Option[StructType]
}