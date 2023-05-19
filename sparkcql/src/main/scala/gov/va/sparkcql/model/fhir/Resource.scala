package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait ResourceLike extends BaseLike {
  val id: Id
  val meta: Option[Meta]
  val implicitRules: Option[Uri]
  val language: Option[Code]
 }
  
final case class Resource (
  id: Id,
  meta: Option[Meta] = None,
  implicitRules: Option[Uri] = None,
  language: Option[Code] = None
) extends ResourceLike