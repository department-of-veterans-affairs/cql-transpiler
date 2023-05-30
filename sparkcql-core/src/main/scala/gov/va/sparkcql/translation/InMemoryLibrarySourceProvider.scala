package gov.va.sparkcql.translation

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.Map
import org.cqframework.cql.cql2elm.LibrarySourceProvider
import org.hl7.elm.r1.VersionedIdentifier

/**
  *
  */
class InMemoryLibrarySourceProvider(libraries: Map[VersionedIdentifier, String]) extends LibrarySourceProvider {

  override def getLibrarySource(libraryIdentifier: VersionedIdentifier): InputStream = {
    val text = this.libraries.get(libraryIdentifier)
    new ByteArrayInputStream(text.getBytes())
  }
}