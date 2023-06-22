package gov.va.sparkcql.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.model.Model

class FileSourceFactory extends SourceFactory {

  override val configurationType: Type = typeOf[FileSourceConfiguration]

  override val defaultConfiguration: Option[SourceConfiguration] = None

  override def create(configuration: Option[SourceConfiguration], models: List[Model], spark: SparkSession): Source = {
    val cfg = configuration.get.asInstanceOf[FileSourceConfiguration]
    new FileSource(models, spark, cfg.path)
  }
}
