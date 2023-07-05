package gov.va.sparkcql.adapter.data

import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.di.ComponentConfiguration

trait DataAdapterFactory {
  
  val defaultConfiguration: Option[ComponentConfiguration]

  def create(configuration: ComponentConfiguration, spark: SparkSession): DataAdapter
}