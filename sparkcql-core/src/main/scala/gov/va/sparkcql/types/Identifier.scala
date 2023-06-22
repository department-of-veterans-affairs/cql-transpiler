package gov.va.sparkcql.types

import org.hl7.elm.r1.VersionedIdentifier

case class Identifier(id: String, system: Option[String], version: Option[String]) {
  override def toString(): String = {
    if (version.isDefined)
      s"""<${id} v'${version.get}'>"""
    else
      s"""<${id}>"""
  }
}

object Identifier {
  def apply(identifier: VersionedIdentifier): Identifier = {
    Identifier(identifier.getId(), Some(identifier.getSystem()), Some(identifier.getVersion()))
  }
}