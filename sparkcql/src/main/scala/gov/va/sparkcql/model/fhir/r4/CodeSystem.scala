package gov.va.sparkcql.model.fhir.r4

trait CodeSystemConceptLike extends BackboneElementLike {
  val code: Code
  val display: Option[String]
  val definition: Option[String]
}

trait CodeSystemLike extends DomainResourceLike {
  val url: Option[Uri]
  val identifier: Option[List[Identifier]]
  val version: Option[String]
  val name: Option[String]
  val title: Option[String]
  val status: String
  val concept: Option[List[CodeSystemConceptLike]]
}

final case class CodeSystem (
  id: Id,
  meta: Option[Meta] = None,
  implicitRules: Option[Uri] = None,
  language: Option[Code] = None,
  text: Option[Narrative] = None,
  contained: Option[List[Resource]] = None,
  url: Option[Uri] = None,
  identifier: Option[List[Identifier]] = None,
  version: Option[String] = None,
  name: Option[String] = None,
  title: Option[String] = None,
  status: String,
  concept: Option[List[CodeSystemConceptLike]] = None,
) extends CodeSystemLike