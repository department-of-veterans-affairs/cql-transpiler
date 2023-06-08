package gov.va.sparkcql.model.providable

import gov.va.sparkcql.model.ext.elm.VersionedIdentifier

case class ValueSetData(identifier: VersionedIdentifier, name: String, includes: Seq[ValueSetIncludeData]) extends ProvidableData
case class ValueSetIncludeData(system: String, codes: Seq[ValueSetCodeData])
case class ValueSetCodeData(code: String, display: String)