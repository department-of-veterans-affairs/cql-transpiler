package gov.va.sparkcql.binding

import gov.va.sparkcql.model.fhir.r4.{Coding, ValueSet, Period}
import scala.reflect.runtime.universe._
import org.apache.spark.sql.Dataset
import gov.va.sparkcql.model.BoundType
import gov.va.sparkcql.model.fhir.r4._
import org.apache.spark.sql.Row
import gov.va.sparkcql.session.Session

/**
  * Predicates used for retrieval operations defined as algebraic data types.
  * TODO: Consider moving these to ELM
  */
trait PredicateLike

case class CodingEqualityPredicate (
  val property: Coding,
  val value: Coding
) extends PredicateLike

case class ValueSetMembershipPredicate (
  val property: Coding,
  val value: ValueSet
) extends PredicateLike

case class DurationBetweenPredicate (
  val property: Coding,
  val start: Date,
  val end: Date
) extends PredicateLike

trait Binding {

  def retrieve[T <: BoundType : TypeTag](filter: Option[List[PredicateLike]]): Option[Dataset[T]]
  protected def boundName[T <: BoundType : TypeTag]: String = {
    typeOf[T].typeSymbol.name.toString
  }
}