package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.core.adapter.model.CompositeModelAdapter
import gov.va.sparkcql.core.adapter.{Adapter, Configuration}

class FileSourceFactory() extends SourceFactory {

  def isDefaultConfigurable(): Boolean = false
  
  def defaultConfiguration: Option[Configuration] = None

  def create(configuration: Option[SourceConfiguration], modelAdapters: CompositeModelAdapter, spark: SparkSession): SourceAdapter = {
    val cfg = configuration.get.asInstanceOf[FileSourceConfiguration]
    new FileSourceAdapter(modelAdapters, spark, cfg.path)
  }
}