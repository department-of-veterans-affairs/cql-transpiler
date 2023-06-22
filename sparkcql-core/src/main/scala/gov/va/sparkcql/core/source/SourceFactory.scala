package gov.va.sparkcql.core.source

import scala.reflect.runtime.universe._
import gov.va.sparkcql.core.model.Model
import org.apache.spark.sql.SparkSession

trait SourceFactory {
  
  val configurationType: Type

  val defaultConfiguration: Option[SourceConfiguration]

  def create(configuration: Option[SourceConfiguration], models: List[Model], spark: SparkSession): Source
}