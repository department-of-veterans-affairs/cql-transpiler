package gov.va.sparkcql.core.model.providable

import gov.va.sparkcql.core.model.elm.VersionedIdentifier

case class LibraryData(identifier: VersionedIdentifier, content: String) extends ProvidableData