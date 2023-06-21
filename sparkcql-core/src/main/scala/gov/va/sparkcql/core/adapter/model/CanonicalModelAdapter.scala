package gov.va.sparkcql.core.adapter.model

import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.core.model.CqlContent
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.core.model.{DataType, ValueSet}
import gov.va.sparkcql.core.translation.cql2elm.CqlCompilerGateway
import gov.va.sparkcql.core.model.VersionedId
import javax.xml.namespace.QName

class CanonicalModelAdapter extends ModelAdapter {

  def namespaceUri(): String = "http://va.gov/sparkcql/core/model"

  def stateCapabilities(): ModelCapabilityStatement = {
    ModelCapabilityStatement(true, true, None)
  }

  def schemaOf(dataType: QName): Option[StructType] = {
    dataType match {
      case x if x == DataType[CqlContent]() => Some(Encoders.product[CqlContent].schema)
      case x if x == DataType[ValueSet]() => Some(Encoders.product[ValueSet].schema)
      case _ => None
    }
  }
}