package gov.va.sparkcql.dataprovider.library

import java.io.ByteArrayInputStream
import java.io.InputStream
import org.hl7.elm.r1.VersionedIdentifier

class InMemoryLibraryDataProvider(libraries: Map[VersionedIdentifier, String]) extends LibraryDataProvider {
  
  override def isDefined(key: VersionedIdentifier): Boolean = {
    libraries.isDefinedAt(key)
  }

  override def fetch(key: VersionedIdentifier): Option[String] = {
    libraries.get(key)
  }
}