package gov.va.sparkcql.adapter.model

import scala.reflect.runtime.universe._
import gov.va.sparkcql.adapter.Composable
import gov.va.sparkcql.model.DataType
import gov.va.sparkcql.model.xsd.QName
import org.apache.spark.sql.types.StructType

class ModelComposite extends ModelAdapter with Composable[ModelAdapter] {

  override def toDataType[T: TypeTag](): DataType = {
    first(f => Option(f.toDataType[T]())).getOrElse(throw new Exception("Unable to find type"))
  }

  override def toDataType(qname: QName): DataType = {
    first(f => Option(f.toDataType(qname))).getOrElse(throw new Exception("Unable to find type"))
  }

  override def schema(dataType: DataType): Option[StructType] = {
    first(f => Option(f.schema(dataType))).getOrElse(throw new Exception("Unable to find schema"))
  }

  override def deserialize[T : TypeTag](data: String): Option[T] = {
    first(f => Option(f.deserialize[T](data))).getOrElse(throw new Exception("Unable to deserialize type"))
  }
}