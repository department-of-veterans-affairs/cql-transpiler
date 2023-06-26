package gov.va.sparkcql.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, DataFrame, Encoders, Dataset, Row}
import javax.xml.namespace.QName
import gov.va.sparkcql.model.DataType
import gov.va.sparkcql.model.Model
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.model.ModelAggregator

trait Source {

  val models: List[Model]
  val modelAggregate = new ModelAggregator(models)
  val spark: SparkSession

  def isDataTypePresent(dataType: QName): Boolean
  
  def acquireData(dataType: QName): Option[DataFrame]

  def acquireData[T <: Product : TypeTag](): Option[Dataset[T]] = {
    val dataType = DataType[T]()
    val encoder = Encoders.product[T]
    val df = acquireData(dataType)
    if (df.isDefined) {
      Some(df.get.as[T](encoder))
    } else {
      None
    }
  }
}