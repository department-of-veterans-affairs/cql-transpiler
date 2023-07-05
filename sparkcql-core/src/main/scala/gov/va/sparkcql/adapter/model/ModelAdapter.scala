package gov.va.sparkcql.adapter.model

import scala.reflect.runtime.universe._
import scala.collection.JavaConverters._
import javax.xml.namespace.QName
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.io.Log

trait ModelAdapter {

  val namespaceUri: String

  val supportedDataTypes: List[QName]

  def assertDataTypeIsSupported(dataType: QName): Unit = {
    if (!supportedDataTypes.contains(dataType)) {
      throw new RuntimeException("Unsupported data type '" + dataType.toString() + "'.")
    }
  }

  def schemaOf(dataType: QName): Option[StructType]
  
  def metaInterval(typeName: String): (String, String)

  def typeToElmMapping(typeName: String): Map[String, String]

  def patientIdentifier(): QName = ???    // TODO: Need a way to identify a patient and relating b/w models
}