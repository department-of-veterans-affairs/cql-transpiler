package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import gov.va.sparkcql.core.adapter.source.SourceAdapterFactory
import gov.va.sparkcql.core.adapter.source.SourceAdapter
import gov.va.sparkcql.core.adapter.model.CompositeModelAdapter
import gov.va.sparkcql.core.adapter.source.SourceAdapterConfig

class SyntheaSourceAdapterFactory() extends SourceAdapterFactory {

  val DefaultPopulationSize = PopulationSize.PopulationSize10

  def isDefaultConfigurable(): Boolean = true

  def create(configuration: SourceAdapterConfig, modelAdapters: CompositeModelAdapter, spark: SparkSession): SourceAdapter = {
    val cfg = configuration.asInstanceOf[SyntheaSourceAdapterConfig]
    instances.getOrElseUpdate(cfg.size, new SyntheaSourceAdapter(modelAdapters, spark, cfg.size))
  }

  def create(modelAdapters: CompositeModelAdapter, spark: SparkSession): SourceAdapter = {
    instances.getOrElseUpdate(DefaultPopulationSize, new SyntheaSourceAdapter(modelAdapters, spark, DefaultPopulationSize))
  }

  val instances = HashMap[PopulationSize, SyntheaSourceAdapter]()
}