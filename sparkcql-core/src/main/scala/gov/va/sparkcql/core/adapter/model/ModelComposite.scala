package gov.va.sparkcql.core.adapter.model

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.adapter.Composable
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.model.xsd.QName
import org.apache.spark.sql.types.StructType

sealed class ModelComposite extends ModelAdapter with Composable[ModelAdapter] {

  override def schema(dataType: DataType): Option[StructType] = {
    first(f => Option(f.schema(dataType))).getOrElse(throw new Exception(s"Unable to find schema ${dataType.toString()}"))
  }

  override def deserialize[T : TypeTag](data: String): Option[T] = {
    first(f => Option(f.deserialize[T](data))).getOrElse(throw new Exception(s"Unable to deserialize type ${typeOf[T].typeSymbol.fullName}"))
  }
}