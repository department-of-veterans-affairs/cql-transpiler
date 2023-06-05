package gov.va.sparkcql.translation

import java.io.ByteArrayInputStream
import java.io.InputStream
import org.hl7.elm.r1.VersionedIdentifier
import org.cqframework.cql.cql2elm.LibrarySourceProvider

/**
  * Implements a bridge between the libary resolver function (defined in CqlCompilerGateway) and
  * the LibraryManager through the IdentifiedLibraryContentProvider interface.
  */
protected class LibrarySourceBridgeProvider(libraryResolver: (VersionedIdentifier) => String) extends LibrarySourceProvider {
  override def getLibrarySource(libraryIdentifier: VersionedIdentifier): InputStream = {
    val text = libraryResolver(libraryIdentifier)
    if (text != null) {
      new ByteArrayInputStream(text.getBytes())
    } else {
      null
    }
  }
}