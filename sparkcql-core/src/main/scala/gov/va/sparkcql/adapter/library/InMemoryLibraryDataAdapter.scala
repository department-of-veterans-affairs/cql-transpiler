package gov.va.sparkcql.adapter.library

import java.io.ByteArrayInputStream
import java.io.InputStream
import org.hl7.elm.r1.VersionedIdentifier

/**
  *
  */
class InMemoryLibraryDataAdapter(libraries: Map[VersionedIdentifier, String]) extends LibraryDataAdapter {
  override def getLibrarySource(libraryIdentifier: VersionedIdentifier): InputStream = {
    val text = this.libraries.get(libraryIdentifier)
    new ByteArrayInputStream(text.get.getBytes())
  }
}