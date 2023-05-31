package gov.va.sparkcql.dataprovider.library

import org.hl7.elm.r1.VersionedIdentifier

abstract class LibraryDataProvider {
  def isDefined(key: VersionedIdentifier): Boolean

  def get(key: VersionedIdentifier): Option[String]
  
  def getOrElse[V1 >: String](key: VersionedIdentifier, default: => V1): V1 = {
    get(key).getOrElse(default)
  }
}