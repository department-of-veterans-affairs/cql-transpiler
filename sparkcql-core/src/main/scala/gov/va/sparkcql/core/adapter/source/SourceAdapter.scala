package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import javax.xml.namespace.QName
import gov.va.sparkcql.core.model.DataType

trait SourceAdapter {

  val spark: SparkSession
  val modelAdapter: ModelAdapter

  def acquireData(dataType: QName): Option[Dataset[Row]]

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