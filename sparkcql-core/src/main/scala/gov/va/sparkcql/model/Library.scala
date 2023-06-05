package gov.va.sparkcql.model

case class LibraryId(id: String, system: Option[String], version: Option[String]) {
  def toPrettyString(): String = {
    if (version.isDefined)
      s"""<library ${id} version '${version.get}'>"""
    else
      s"""<library ${id}>"""
  }
}

object LibraryId {
  def apply(identifier: org.hl7.elm.r1.VersionedIdentifier): LibraryId = {
    LibraryId(identifier.getId(), Some(identifier.getSystem()), Some(identifier.getVersion()))
  }
}

case class IdentifiedLibraryContent(identifier: LibraryId, content: String)