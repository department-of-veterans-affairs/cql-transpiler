package gov.va.sparkcql.core.model

import javax.xml.namespace.QName
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.core.types._

class CoreModel extends Model {

  override val namespaceUri: String = "http://va.gov/sparkcql/types"

  override val supportedDataTypes: List[QName] = {
    List(
      DataType[IdentifiedContent],
      DataType[ValueSet]
    )
  }

  override def schemaOf(dataType: QName): Option[StructType] = {
    dataType match {
      case x if x == DataType[IdentifiedContent]() => Some(Encoders.product[IdentifiedContent].schema)
      case x if x == DataType[ValueSet]() => Some(Encoders.product[ValueSet].schema)
      case _ => None
    }
  }

}