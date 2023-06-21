package gov.va.sparkcql.core.adapter.source

import gov.va.sparkcql.core.adapter.Factory
import gov.va.sparkcql.core.adapter.model.CompositeModelAdapter
import org.apache.spark.sql.SparkSession

trait SourceFactory extends Factory {
  
  def create(configuration: Option[SourceConfiguration], modelAdapters: CompositeModelAdapter, spark: SparkSession): SourceAdapter
}