package gov.va.sparkcql.core.native.model

import gov.va.sparkcql.core.model.Model
import javax.xml.namespace.QName
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.core.model.{CqlContent, ValueSet, DataType}

class NativeModel extends Model {

  override val namespaceUri: String = "http://va.gov/sparkcql/core/native"

  override val supportedDataTypes: List[QName] = {
    List(
      DataType[CqlContent],
      DataType[ValueSet]
    )
  }

  override def schemaOf(dataType: QName): Option[StructType] = {
    dataType match {
      case x if x == DataType[CqlContent]() => Some(Encoders.product[CqlContent].schema)
      case x if x == DataType[ValueSet]() => Some(Encoders.product[ValueSet].schema)
      case _ => None
    }
  }
}