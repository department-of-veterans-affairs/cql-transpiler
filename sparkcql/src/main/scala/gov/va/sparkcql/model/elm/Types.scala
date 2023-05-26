package gov.va.sparkcql.model.elm

object primitive {
  type Date = java.sql.Date
  type DateTime = java.sql.Timestamp
}
  
// final case class VersionedIdentifier (
//   system: String,
//   id: String,
//   version: Option[String] = None
// )  

final case class Code (
  code: String,
  display: Option[String] = None,
  system: Option[String] = None,
  version: Option[String] = None
)

final case class ValueSet (
  code: String
  // TODO
)