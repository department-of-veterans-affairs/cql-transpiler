package gov.va.sparkcql.core.adapter.model

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.model.xsd.QName
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.core.model.CqlContent
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.core.model.ValueSet
import gov.va.sparkcql.core.translation.cql2elm.CqlCompilerGateway
import gov.va.sparkcql.core.model.VersionedId

class NativeModel extends ModelAdapterFactory {
  def create(): ModelAdapter = new NativeModelAdapter()
}
