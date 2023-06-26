package gov.va.sparkcql.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.types.StructType
import gov.va.sparkcql.logging.Log
import javax.xml.namespace.QName
import gov.va.sparkcql.model.Model

sealed class SourceAggregator(sources: List[Source]) extends Source {

  override val models: List[Model] = List()

  override val spark: SparkSession = null

  override def isDataTypePresent(dataType: QName): Boolean = {
    sources.filter(_.isDataTypePresent(dataType)).length > 0
  }
  
  override def acquireData(dataType: QName): Option[DataFrame] = {
    val eligibleAdapters = sources.filter(_.isDataTypePresent(dataType))
    if (!eligibleAdapters.isEmpty) {
      val acquiredDf = eligibleAdapters.flatMap(a => {
        val df = a.acquireData(dataType)
        if (df.isEmpty) {
          Log.warn(s"${a.getClass().getSimpleName()} stated support for data type '${dataType.toString()}' but none was found.")
          None
        } else if (df.get.schema.fields.length == 0) {
          Log.warn(s"${a.getClass().getSimpleName()} returned a columnless dataframe when None should have been returned. Ignoring output.")
          None
        } else {
          df
        }
      })
      Some(acquiredDf.reduce(_.union(_)))   // union all dataframes
    } else {
      Log.warn(s"Attempted to acquire missing data for type '${dataType.toString()}' without verifying data was present.")
      None
    }
  }
}