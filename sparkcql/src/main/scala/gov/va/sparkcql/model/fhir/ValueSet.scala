package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait ValueSetExpansionContainsLike extends BackboneElementLike {
  val system: Option[Uri]
  val version: Option[String]
  val code: Option[Code]
  val display: Option[Code]
  val effectivePeriod: Option[Period]
}

final case class ValueSetExpansionContains (
  id: Option[String] = None,
  system: Option[Uri],
  version: Option[String],
  code: Option[Code],
  display: Option[Code],
  effectivePeriod: Option[Period]  
) extends ValueSetExpansionContainsLike

trait ValueSetExpansionLike extends BackboneElementLike {
  val identifier: Option[Identifier]
  val timestamp: DateTime
  val total: Option[Int]
  val contains: Option[Seq[ValueSetExpansionContains]]
}

final case class ValueSetExpansion (
  val id: Option[String] = None,
  val identifier: Option[Identifier] = None,
  val timestamp: DateTime,
  val total: Option[Int] = None,
  val contains: Option[Seq[ValueSetExpansionContains]] = None
) extends ValueSetExpansionLike

trait ValueSetLike extends DomainResourceLike {
  val url: Option[Uri]
  val identifier: Option[Identifier]
  val version: Option[String]
  val name: Option[String]
  val title: Option[String]
  val status: Option[String]
  val date: Option[DateTime]
  val publisher: Option[String]
  val description: Option[Markdown]
  val expansion: Option[ValueSetExpansion]
}

final case class ValueSet (
  id: Id,
  meta: Option[Meta] = None,
  implicitRules: Option[Uri] = None,
  language: Option[Code] = None,
  text: Option[Narrative] = None,
  contained: Option[List[Resource]] = None,
  url: Option[Uri] = None,
  identifier: Option[Identifier] = None,
  version: Option[String] = None,
  name: Option[String] = None,
  title: Option[String] = None,
  status: Option[String] = None,
  date: Option[DateTime] = None,
  publisher: Option[String] = None,
  description: Option[Markdown] = None,
  expansion: Option[ValueSetExpansion] = None
) extends ValueSetLike