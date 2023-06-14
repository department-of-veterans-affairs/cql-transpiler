package gov.va.sparkcql.core.adapter.model

import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.model.xsd.QName

abstract class ModelAdapter() {

  def schema(dataType: DataType): Option[StructType]

  def deserialize[T : TypeTag](data: String): Option[T]
}