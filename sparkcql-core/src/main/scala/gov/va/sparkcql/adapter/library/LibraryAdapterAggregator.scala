package gov.va.sparkcql.adapter.library

import org.hl7.elm.r1.VersionedIdentifier

sealed class LibraryAdapterAggregator(libraryAdapters: List[LibraryAdapter]) extends LibraryAdapter {
  
  def getLibraryContent(identifier: VersionedIdentifier): Option[String] = {
    val found = libraryAdapters.flatMap(_.getLibraryContent(identifier))
    found.headOption
  }
}