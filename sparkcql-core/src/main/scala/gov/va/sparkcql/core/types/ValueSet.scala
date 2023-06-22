package gov.va.sparkcql.core.types

case class ValueSet(identifier: Identifier, name: String, codes: Seq[ValueSetCode])
case class ValueSetCode(system: String, code: String, display: String)