package gov.va.sparkcql.dataprovider

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap

sealed trait PopulationSize

object PopulationSize {
  final case object PopulationSize10 extends PopulationSize
  final case object PopulationSize1000 extends PopulationSize
}

class SyntheaDataProvider(size: PopulationSize) extends DataProvider {
  val instances = HashMap[PopulationSize, SyntheaDataAdapter]()

  def createAdapter(spark: SparkSession): DataAdapter = {
    instances.getOrElseUpdate(size, new SyntheaDataAdapter(spark, size))
  }
}

object SyntheaDataProvider {
  def apply(size: PopulationSize) = new SyntheaDataProvider(size)
}