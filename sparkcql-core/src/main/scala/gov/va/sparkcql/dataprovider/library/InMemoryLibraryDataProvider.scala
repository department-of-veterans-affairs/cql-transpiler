package gov.va.sparkcql.dataprovider.library

import java.io.ByteArrayInputStream
import java.io.InputStream
import org.hl7.elm.r1.VersionedIdentifier
import scala.collection.mutable.HashMap

class InMemoryLibraryDataProvider(libraries: Map[VersionedIdentifier, String]) extends LibraryDataProvider {
  
  override def isDefined(key: VersionedIdentifier): Boolean = {
    libraries.isDefinedAt(key)
  }

  override def fetchOne(key: VersionedIdentifier): Option[String] = {
    val versioned = libraries.get(key)
    if (versioned.isDefined)
      versioned
    else {
      val unversioned = libraries.filter(_._1.getId().toUpperCase() == key.getId().toUpperCase())
      Some(unversioned.head._2)
    }
  }
}