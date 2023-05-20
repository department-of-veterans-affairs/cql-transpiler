package gov.va.sparkcql.model.fhir.r4

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