package gov.va.sparkcql.model.elm

final case class VersionedIdentifier(id: String, version: Option[String], system: String)