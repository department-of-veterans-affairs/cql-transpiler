package gov.va.sparkcql.model

import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import javax.xml.namespace.QName
import gov.va.sparkcql.logging.Log
import javax.naming.OperationNotSupportedException

sealed class ModelAggregator(models: List[Model]) extends Model {

  lazy val namespaceUri: String = throw new OperationNotSupportedException()

  lazy val supportedDataTypes: List[QName] = {
    models.flatMap(_.supportedDataTypes).distinct
  }

  def resolveModel(dataType: QName): Option[Model] = {
    models.filter(_.supportedDataTypes.contains(dataType)).headOption
  }
  
  def ensureDataTypeSupported(dataType: QName): Unit = {
    if (supportedDataTypes.contains(dataType)) {
      throw new UnsupportedOperationException(String.format("No model adapter supports this operation for type '%s'.", dataType.toString()));
    }
  }

  def schemaOf(dataType: QName): Option[StructType] = {
    val resolved = resolveModel(dataType)
    if (resolved.isDefined) {
      resolved.get.schemaOf(dataType)
    } else {
      Log.warn(s"Unable to find schema ${dataType.toString()}")
      None
    } 
  }

  def metaInterval(typeName: String): (String, String) = ("low", "high")    // TODO: Throw error

  def typeToElmMapping(typeName: String): Map[String, String] = throw new OperationNotSupportedException()
}