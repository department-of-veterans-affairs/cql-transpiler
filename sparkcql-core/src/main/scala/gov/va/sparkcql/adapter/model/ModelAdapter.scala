package gov.va.sparkcql.adapter.model

import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.model.DataType
import gov.va.sparkcql.model.xsd.QName

abstract class ModelAdapter() {

  def toDataType[T : TypeTag](): DataType

  def toDataType(qname: QName): DataType

  def schema(dataType: DataType): Option[StructType]

  def deserialize[T : TypeTag](data: String): Option[T]
}