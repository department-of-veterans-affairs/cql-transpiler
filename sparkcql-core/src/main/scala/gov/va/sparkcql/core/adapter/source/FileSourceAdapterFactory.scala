package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.core.adapter.model.CompositeModelAdapter

class FileSourceAdapterFactory() extends SourceAdapterFactory {

  def isDefaultConfigurable(): Boolean = false
  
  def create(configuration: SourceAdapterConfig, modelAdapters: CompositeModelAdapter, spark: SparkSession): SourceAdapter = {
    val cfg = configuration.asInstanceOf[FileSourceAdapterConfig]
    new FileSourceAdapter(modelAdapters, spark, cfg.path)
  }
  
  def create(modelAdapters: CompositeModelAdapter, spark: SparkSession): SourceAdapter = {
    new FileSourceAdapter(modelAdapters, spark, "./")
  }
}