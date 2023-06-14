package gov.va.sparkcql.core.adapter.model

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.adapter.Composable
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.model.xsd.QName
import org.apache.spark.sql.types.StructType

sealed class ModelComposite extends ModelAdapter with Composable[ModelAdapter] {

  override def schema(dataType: DataType): Option[StructType] = {
    composeFirst[StructType](f => {
      f.schema(dataType)
    }).orElse(throw new Exception(s"Unable to find schema ${dataType.toString()}"))
  }

  override def deserialize[T : TypeTag](data: String): Option[T] = {
    composeFirst[T](f => {
      f.deserialize[T](data)
    }).orElse(throw new Exception(s"Unable to deserialize type ${typeOf[T].typeSymbol.fullName}"))
  }
}