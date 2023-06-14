package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.adapter.Composable
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.model.xsd.QName
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.core.adapter.model.ModelAdapter

sealed class SourceComposite(spark: SparkSession, modelAdapter: ModelAdapter)
    extends SourceAdapter(spark, modelAdapter) with Composable[SourceAdapter] {

  def read(dataType: DataType): Option[Dataset[Row]] = {
    composeFirst(a => {
      val df = a.read(dataType)
      if (df.isDefined && df.get.schema.fields.length > 0) {
        df
      } else {
        println("WARNING: SourceAdapter returned a columnless dataframe when None should have been returned. Ignoring output.")
        None
      }
    })
  }
}