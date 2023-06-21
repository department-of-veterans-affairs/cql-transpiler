package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import gov.va.sparkcql.core.adapter.AdapterFactory
import gov.va.sparkcql.core.adapter.model.CompositeModelAdapter

trait SourceAdapterFactory extends AdapterFactory {

  def create(configuration: SourceAdapterConfig, modelAdapters: CompositeModelAdapter, spark: SparkSession): SourceAdapter
  
  def create(modelAdapters: CompositeModelAdapter, spark: SparkSession): SourceAdapter
}