package gov.va.sparkcql.dataprovider.library

import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.dataprovider.Fetchable

abstract class LibraryDataProvider extends Fetchable[String, VersionedIdentifier] {
  
  def isDefined(key: VersionedIdentifier): Boolean

  def fetchOne(key: VersionedIdentifier): Option[String]
}