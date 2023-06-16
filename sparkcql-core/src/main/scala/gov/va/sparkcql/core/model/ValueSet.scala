package gov.va.sparkcql.core.model

case class ValueSet(identifier: VersionedId, name: String, codes: Seq[ValueSetCode])
case class ValueSetCode(system: String, code: String, display: String)