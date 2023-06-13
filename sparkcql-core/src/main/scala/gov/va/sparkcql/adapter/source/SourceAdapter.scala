package gov.va.sparkcql.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.adapter.model.ModelAdapter
import gov.va.sparkcql.model.DataType

abstract class SourceAdapter(spark: SparkSession, modelAdapter: ModelAdapter) {

  def read(dataType: DataType): Dataset[Row]

  def read[T <: Product : TypeTag](): Dataset[T] = {
    val modelTypeRef = DataType[T]
    val encoder = Encoders.product[T]
    read(modelTypeRef).as[T](encoder)
  }
}