package gov.va.sparkcql.binding.strategy

import org.apache.spark.sql.DataFrame
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.model.fhir.Primitive._
import org.apache.spark.sql.SparkSession

trait BindingStrategy {
  def resolve(spark: SparkSession, resourceType: Coding, code: Option[Coding], startDate: Option[DateTime], endDate: Option[DateTime]): DataFrame
}