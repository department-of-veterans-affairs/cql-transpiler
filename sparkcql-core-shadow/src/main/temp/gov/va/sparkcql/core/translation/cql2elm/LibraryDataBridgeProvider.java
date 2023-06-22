package gov.va.sparkcql.core.translator.cql2elm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Function;

import org.hl7.elm.r1.VersionedIdentifier;
import org.cqframework.cql.cql2elm.LibrarySourceProvider;

/**
  * Implements a bridge between the libary resolver function (defined in CqlCompilerGateway) and
  * the LibraryManager through the LibraryDataProvider interface.
  */
class LibraryDataBridgeProvider implements LibrarySourceProvider {
  
  Function<VersionedIdentifier, String> libraryResolver;

  protected LibraryDataBridgeProvider(Function<VersionedIdentifier, String> libraryResolver) {
    this.libraryResolver = libraryResolver;
  }

  public InputStream getLibrarySource(VersionedIdentifier libraryIdentifier) {
    String text = libraryResolver.apply(libraryIdentifier);
    if (text != null) {
      return new ByteArrayInputStream(text.getBytes());
    } else {
      return null;
    }
  }
}