package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait ResourceLike extends BaseLike {
  def id: Id
  def meta: Option[Meta]
  def implicitRules: Option[Uri]
  def language: Option[Code]
 }
  
final case class Resource (
  id: Id,
  meta: Option[Meta] = None,
  implicitRules: Option[Uri] = None,
  language: Option[Code] = None
) extends ResourceLike