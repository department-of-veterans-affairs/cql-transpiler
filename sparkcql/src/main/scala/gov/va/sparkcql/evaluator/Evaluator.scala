package gov.va.sparkcql.evaluator

import gov.va.sparkcql.model.fhir.Coding
import gov.va.sparkcql.binding.Bindable
import gov.va.sparkcql.model.fhir.Primitive
import scala.reflect.runtime.universe._
import org.apache.spark.sql.{Dataset}

class Evaluator(bindingMatcher: (Coding) => Option[Bindable]) extends Bindable {
  def evaluate(): Unit = {
  }

  def resolve[T <: Product : TypeTag](resourceType: Coding, code: Option[Coding], startDate: Option[Primitive.DateTime], endDate: Option[Primitive.DateTime]): Dataset[T] = {
    val binding = bindingMatcher(resourceType)
    binding.getOrElse(throw new Exception(s"No binding found for ${resourceType.code}"))
      .resolve[T](resourceType, code, startDate, endDate)
  }
}