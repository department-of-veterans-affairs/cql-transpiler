package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap
import gov.va.sparkcql.core.source.{Source, SourceFactory, SourceConfiguration}
import gov.va.sparkcql.core.model.Model

class SyntheaSourceFactory() extends SourceFactory {

  override val configurationType: Type = typeOf[SyntheaSourceConfiguration]

  override val defaultConfiguration: Option[SourceConfiguration] = Some(SyntheaSourceConfiguration(PopulationSize.PopulationSize10))

  override def create(configuration: Option[SourceConfiguration], models: List[Model], spark: SparkSession): Source = {
    val cfg = configuration.get.asInstanceOf[SyntheaSourceConfiguration]
    instances.getOrElseUpdate(cfg.size, new SyntheaSource(models, spark, cfg.size))
  }

  val instances = HashMap[PopulationSize, SyntheaSource]()
}