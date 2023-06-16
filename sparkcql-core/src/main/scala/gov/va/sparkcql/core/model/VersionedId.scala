package gov.va.sparkcql.core.model

import org.hl7.elm.r1.VersionedIdentifier

case class VersionedId(id: String, system: Option[String], version: Option[String]) {
  override def toString(): String = {
    if (version.isDefined)
      s"""<library ${id} version '${version.get}'>"""
    else
      s"""<library ${id}>"""
  }
}

object VersionedId {
  def apply(identifier: VersionedIdentifier): VersionedId = {
    VersionedId(identifier.getId(), Some(identifier.getSystem()), Some(identifier.getVersion()))
  }
}