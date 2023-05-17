package gov.va.sparkcql.binding

import org.apache.spark.sql.DataFrame
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.model.fhir.Primitive._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.model.fhir.Coding
import scala.reflect.runtime.universe._
import org.apache.spark.sql.Dataset

abstract class Binding(spark: SparkSession) {
  def resolve[T <: Product : TypeTag](resourceType: Coding, code: Option[Coding] = None, startDate: Option[DateTime] = None, endDate: Option[DateTime] = None): Dataset[T]
}