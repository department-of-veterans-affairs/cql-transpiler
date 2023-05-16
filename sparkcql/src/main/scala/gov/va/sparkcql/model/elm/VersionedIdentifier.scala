package gov.va.sparkcql.model.elm

final case class VersionedIdentifier(system: String, id: String, version: Option[String] = None)