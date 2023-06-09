package gov.va.sparkcql.model.elm

case class VersionedIdentifier(id: String, system: Option[String], version: Option[String]) {
  override def toString(): String = {
    if (version.isDefined)
      s"""<library ${id} version '${version.get}'>"""
    else
      s"""<library ${id}>"""
  }
}

object VersionedIdentifier {
  def apply(identifier: org.hl7.elm.r1.VersionedIdentifier): VersionedIdentifier = {
    VersionedIdentifier(identifier.getId(), Some(identifier.getSystem()), Some(identifier.getVersion()))
  }
}