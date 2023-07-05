package gov.va.sparkcql.adapter.library

import org.hl7.elm.r1.VersionedIdentifier

trait LibraryAdapter {

  def getLibraryContent(identifier: VersionedIdentifier): Option[String]
}