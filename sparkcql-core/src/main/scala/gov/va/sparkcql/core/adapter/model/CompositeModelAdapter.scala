package gov.va.sparkcql.core.adapter.model

import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import javax.xml.namespace.QName
import gov.va.sparkcql.core.Log

sealed class CompositeModelAdapter(adapters: List[ModelAdapter]) extends ModelAdapter {

  def this(adapter: ModelAdapter) {
    this(List(adapter))
  }

  def namespaceUri(): String = throw new Exception("Aggregating namespaces is invalid.")

  def stateCapabilities(): ModelCapabilityStatement = {
    adapters.map(_.stateCapabilities()).foldLeft(NoneCapabilityStatement)((s, t) => {
      ModelCapabilityStatement(
        s.cqlModelSupport || t.cqlModelSupport,
        s.valueSetModelSupport || t.valueSetModelSupport,
        Some((s.supportedDataTypes.get ++ t.supportedDataTypes.get).distinct)
      )
    })
  }

  def resolveAdapter(dataType: QName): Option[ModelAdapter] = {
    adapters.filter(_.stateCapabilities.supportedDataTypes.contains(dataType)).headOption
  }
  
  def ensureDataTypeSupported(dataType: QName): Unit = {
    if (stateCapabilities.supportedDataTypes.contains(dataType)) {
      throw new UnsupportedOperationException(String.format("No model adapter supports this operation for type '%s'.", dataType.toString()));
    }
  }

  def schemaOf(dataType: QName): Option[StructType] = {
    val resolved = resolveAdapter(dataType)
    if (resolved.isDefined) {
      resolved.get.schemaOf(dataType)
    } else {
      Log.warn(s"Unable to find schema ${dataType.toString()}")
      None
    } 
  }
}