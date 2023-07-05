package gov.va.sparkcql.synthea

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import gov.va.sparkcql.di.ComponentConfiguration
import gov.va.sparkcql.adapter.data.{DataAdapter, DataAdapterFactory}

class SyntheaDataAdapterFactory() extends DataAdapterFactory {

  override val defaultConfiguration: Option[ComponentConfiguration] = {
    val configuration = new ComponentConfiguration()
    configuration.write("size", "none")
    Some(configuration)
  }

  override def create(configuration: ComponentConfiguration, spark: SparkSession): DataAdapter = {
    val size = configuration.read("sparkcql.syntheadataadapter.samplesize") match {
      case "10" => SampleSize.SampleSize10
      case "1000" => SampleSize.SampleSize1000
      case _ => SampleSize.SampleSizeNone
    }
    new SyntheaDataAdapter(spark, size)
  }
}