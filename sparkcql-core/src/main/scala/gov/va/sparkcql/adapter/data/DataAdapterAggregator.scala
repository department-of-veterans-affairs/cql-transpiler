package gov.va.sparkcql.adapter.data

import org.apache.spark.sql.{SparkSession, DataFrame}
import gov.va.sparkcql.logging.Log
import javax.xml.namespace.QName

sealed class DataAdapterAggregator(dataAdapters: List[DataAdapter]) extends DataAdapter {

  override val spark: SparkSession = null

  override def isDataTypeDefined(dataType: QName): Boolean = {
    dataAdapters.filter(_.isDataTypeDefined(dataType)).length > 0
  }
  
  override def acquire(dataType: QName): Option[DataFrame] = {
    val eligibleAdapters = dataAdapters.filter(_.isDataTypeDefined(dataType))
    if (!eligibleAdapters.isEmpty) {
      val acquiredDf = eligibleAdapters.flatMap(a => {
        val df = a.acquire(dataType)
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