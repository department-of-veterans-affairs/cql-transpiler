package gov.va.sparkcql.core.translator.cql2elm

import java.io.ByteArrayInputStream
import java.io.InputStream
import org.hl7.elm.r1.VersionedIdentifier
import org.cqframework.cql.cql2elm.LibrarySourceProvider

/**
  * Implements a bridge between the libary resolver function (defined in CqlCompilerGateway) and
  * the LibraryManager through the LibraryDataProvider interface.
  */
protected class LibraryDataBridgeProvider(libraryResolver: (VersionedIdentifier) => String) extends LibrarySourceProvider {
  override def getLibrarySource(libraryIdentifier: VersionedIdentifier): InputStream = {
    val text = libraryResolver(libraryIdentifier)
    if (text != null) {
      new ByteArrayInputStream(text.getBytes())
    } else {
      null
    }
  }
}