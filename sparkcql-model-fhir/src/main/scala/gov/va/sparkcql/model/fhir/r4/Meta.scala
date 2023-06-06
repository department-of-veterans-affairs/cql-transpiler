package gov.va.sparkcql.model.fhir.r4

import gov.va.sparkcql.model.fhir.primitives._

trait MetaLike extends ElementLike {
  val versionId: Option[Id]
  val text: Option[String]
  val lastUpdated: Option[Instant]
  val source: Option[Uri]
  val security: Option[List[Coding]]
  val tag: Option[List[Coding]]
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