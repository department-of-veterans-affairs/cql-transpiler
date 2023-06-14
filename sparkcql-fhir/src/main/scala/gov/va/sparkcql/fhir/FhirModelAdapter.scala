package gov.va.sparkcql.fhir

import gov.va.sparkcql.core.adapter.model.ModelAdapter
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.model.xsd.QName
import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType

class FhirModelAdapter extends ModelAdapter {

  override def schema(dataType: DataType): Option[StructType] = {
    None
  }

  override def deserialize[T: TypeTag](data: String): Option[T] = ???
  
}
