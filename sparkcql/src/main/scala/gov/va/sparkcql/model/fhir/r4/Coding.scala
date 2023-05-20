package gov.va.sparkcql.model.fhir.r4

trait CodingLike extends ElementLike {
  val system: Uri
  val code: Code
  val version: Option[String]
  val display: Option[String]
  val userSelected: Option[String]
}

final case class Coding (
  system: Uri,
  code: Code,
  version: Option[String] = None,
  display: Option[String] = None,
  userSelected: Option[String] = None,
  id: Option[String] = None
) extends CodingLike