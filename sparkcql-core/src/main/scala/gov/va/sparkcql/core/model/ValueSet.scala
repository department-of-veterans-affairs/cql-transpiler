package gov.va.sparkcql.core.model

import gov.va.sparkcql.core.model.elm.VersionedIdentifier

case class ValueSet(identifier: VersionedIdentifier, name: String, codes: Seq[ValueSetCode])
case class ValueSetCode(system: String, code: String, display: String)