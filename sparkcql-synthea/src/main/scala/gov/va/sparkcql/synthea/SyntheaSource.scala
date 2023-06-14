package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import gov.va.sparkcql.core.adapter.source.SourceAdapterFactory
import gov.va.sparkcql.core.adapter.source.SourceAdapter

sealed trait PopulationSize

object PopulationSize {
  final case object PopulationSize10 extends PopulationSize
  final case object PopulationSize1000 extends PopulationSize
}

class SyntheaSource(size: PopulationSize) extends SourceAdapterFactory {
  val instances = HashMap[PopulationSize, SyntheaSourceAdapter]()

  def create(spark: SparkSession, modelAdapter: ModelAdapter): SourceAdapter = {
    instances.getOrElseUpdate(size, new SyntheaSourceAdapter(spark, size, modelAdapter))
  }
}

object SyntheaSource {
  def apply(size: PopulationSize) = new SyntheaSource(size)
}