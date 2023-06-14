package gov.va.sparkcql.fhir

package object primitives {
  type Uri = String
  type Code = String
  type Id = String
  type Date = java.sql.Date
  type DateTime = java.sql.Timestamp
  type Instant = DateTime
  type Xhtml = String
  type Markdown = String
}