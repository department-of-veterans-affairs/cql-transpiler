package gov.va.sparkcql.model.providable

import gov.va.sparkcql.model.ext.elm.VersionedIdentifier

case class LibraryData(identifier: VersionedIdentifier, content: String) extends ProvidableData