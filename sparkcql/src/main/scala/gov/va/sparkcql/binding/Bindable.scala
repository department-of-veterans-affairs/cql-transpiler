package gov.va.sparkcql.binding

import gov.va.sparkcql.model.fhir.{Coding}
import gov.va.sparkcql.model.fhir.Primitive._
import scala.reflect.runtime.universe._
import org.apache.spark.sql.Dataset

trait Bindable {
  def resolve[T <: Product : TypeTag](resourceType: Coding, code: Option[Coding] = None, startDate: Option[DateTime] = None, endDate: Option[DateTime] = None): Dataset[T]
}