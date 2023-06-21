package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap
import gov.va.sparkcql.core.adapter.source.{SourceFactory, SourceAdapter, SourceConfiguration}
import gov.va.sparkcql.core.adapter.model.{CompositeModelAdapter, ModelAdapter}
import gov.va.sparkcql.core.adapter.Configuration

class SyntheaSourceFactory() extends SourceFactory {

  def isDefaultConfigurable(): Boolean = true
  
  override def defaultConfiguration: Option[Configuration] = Some(SyntheaSourceConfiguration(PopulationSize.PopulationSize10))

  override def create(configuration: Option[SourceConfiguration], modelAdapters: CompositeModelAdapter, spark: SparkSession): SourceAdapter = {
    val cfg = configuration.get.asInstanceOf[SyntheaSourceConfiguration]
    instances.getOrElseUpdate(cfg.size, new SyntheaSourceAdapter(modelAdapters, spark, cfg.size))
  }

  val instances = HashMap[PopulationSize, SyntheaSourceAdapter]()
}