package gov.va.sparkcql.model

import gov.va.sparkcql.model.elm.VersionedIdentifier

case class ValueSet(identifier: VersionedIdentifier, name: String, codes: Seq[ValueSetCode])
case class ValueSetCode(system: String, code: String, display: String)