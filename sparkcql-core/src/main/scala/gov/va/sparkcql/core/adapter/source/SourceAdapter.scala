package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import gov.va.sparkcql.core.model.DataType

abstract class SourceAdapter(spark: SparkSession, modelAdapter: ModelAdapter) {

  def read(dataType: DataType): Option[Dataset[Row]]

  def read[T <: Product : TypeTag](): Option[Dataset[T]] = {
    val modelTypeRef = DataType[T]
    val encoder = Encoders.product[T]
    val df = read(modelTypeRef)
    if (df.isDefined) {
      Some(df.get.as[T](encoder))
    } else {
      None
    }
  }
}