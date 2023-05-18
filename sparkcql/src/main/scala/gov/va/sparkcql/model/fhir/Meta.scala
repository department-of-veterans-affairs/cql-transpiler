package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait MetaLike extends ElementLike {
  def versionId: Option[Id]
  def text: Option[String]
  def lastUpdated: Option[Instant]
  def source: Option[Uri]
  def security: Option[List[Coding]]
  def tag: Option[List[Coding]]
}

final case class Meta (
  versionId: Option[Id] = None,
  text: Option[String] = None,
  lastUpdated: Option[Instant] = None,
  source: Option[Uri] = None,
  security: Option[List[Coding]] = None,
  tag: Option[List[Coding]] = None,
  id: Option[String] = None
) extends MetaLike