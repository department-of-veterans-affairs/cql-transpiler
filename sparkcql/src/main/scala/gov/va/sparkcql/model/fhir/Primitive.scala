package gov.va.sparkcql.model.fhir

object Primitive {
  type Uri = String
  type Code = String
  type Id = String
  type Date = java.sql.Date
  type DateTime = java.sql.Timestamp
  type Instant = DateTime
  type Xhtml = String
}