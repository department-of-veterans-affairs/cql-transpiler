package gov.va.sparkcql.model

case class ValueSetData(identifier: VersionedIdentifier, name: String, includes: Seq[ValueSetIncludeData]) extends ProvidableData
case class ValueSetIncludeData(system: String, codes: Seq[ValueSetCodeData])
case class ValueSetCodeData(code: String, display: String)