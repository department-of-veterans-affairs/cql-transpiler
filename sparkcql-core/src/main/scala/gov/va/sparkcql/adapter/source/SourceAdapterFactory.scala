package gov.va.sparkcql.adapter.source

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import gov.va.sparkcql.adapter.model.ModelAdapter

trait SourceAdapterFactory {
  def create(spark: SparkSession, modelAdapter: ModelAdapter): SourceAdapter
}