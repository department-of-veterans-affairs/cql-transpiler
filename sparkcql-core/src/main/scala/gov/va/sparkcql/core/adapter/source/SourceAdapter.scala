package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import javax.xml.namespace.QName
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.adapter.Adapter
import gov.va.sparkcql.core.adapter.model.CompositeModelAdapter

trait SourceAdapter extends Adapter {

  val spark: SparkSession
  val modelAdapters: CompositeModelAdapter
  
  def isDataTypePresent(dataType: QName): Boolean
  
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