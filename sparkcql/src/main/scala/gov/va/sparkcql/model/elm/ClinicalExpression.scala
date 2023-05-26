package gov.va.sparkcql.model.elm

/**
  * https://cql.hl7.org/elm/schema/clinicalexpression.xsd
  */

/**
  * 
  */
trait CodeFilterElementLike extends ExpressionLike {
  val property: Option[String]
  val valueSetProperty: Option[String]
  val search: Option[String]
  // TODO
 }

final case class CodeFilterElement (
  property: Option[String],
  valueSetProperty: Option[String],
  search: Option[String]
  // TODO
) extends CodeFilterElementLike

/**
  * 
  */

final case class DateFilterElement()