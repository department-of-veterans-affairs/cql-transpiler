package gov.va.sparkcql.model.providable

import gov.va.sparkcql.model.elm.VersionedIdentifier

case class LibraryData(identifier: VersionedIdentifier, content: String) extends ProvidableData