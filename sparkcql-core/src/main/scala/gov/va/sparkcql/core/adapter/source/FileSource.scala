package gov.va.sparkcql.core.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.core.adapter.model.ModelAdapter

class FileSource(path: String) extends SourceAdapterFactory {
  
  def create(spark: SparkSession, modelAdapter: ModelAdapter): SourceAdapter = {
    new FileSourceAdapter(modelAdapter, spark, path)
  }
}

object FileSource {
  def apply(path: String) = new FileSource(path)
}