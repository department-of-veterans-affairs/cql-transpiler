package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import gov.va.sparkcql.core.Log
import javax.xml.namespace.QName

class CompositeSourceAdapter(val modelAdapter: ModelAdapter, val spark: SparkSession, adapters: List[SourceAdapter]) extends SourceAdapter {

  protected def composeFirst[R](f: (SourceAdapter) => Option[R]): Option[R] = {
    adapters
      .view
      .flatMap(a => {
        f(a)
      }).headOption
  }
  
  def acquireData(dataType: QName): Option[Dataset[Row]] = {
    composeFirst(a => {
      val df = a.acquireData(dataType)
      if (df.isDefined) {
        if (df.get.schema.fields.length > 0) {
          df
        } else {
          Log.warn(s"${a.getClass().getSimpleName()} returned a columnless dataframe when None should have been returned. Ignoring output.")
          None
        }
      } else {
        None
      }
    })
  }
}