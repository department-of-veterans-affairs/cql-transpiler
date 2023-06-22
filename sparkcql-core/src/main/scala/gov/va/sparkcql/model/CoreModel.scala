package gov.va.sparkcql.model

import javax.xml.namespace.QName
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.types._

class CoreModel extends Model {

  override val namespaceUri: String = "http://va.gov/sparkcql/types"

  override val supportedDataTypes: List[QName] = {
    List(
      DataType[IdentifiedText],
      DataType[ValueSet]
    )
  }

  override def schemaOf(dataType: QName): Option[StructType] = {
    dataType match {
      case x if x == DataType[IdentifiedText]() => Some(Encoders.product[IdentifiedText].schema)
      case x if x == DataType[ValueSet]() => Some(Encoders.product[ValueSet].schema)
      case _ => None
    }
  }

}