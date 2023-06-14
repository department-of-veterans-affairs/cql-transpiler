package gov.va.sparkcql.core.model

import gov.va.sparkcql.core.model.elm.VersionedIdentifier

case class CqlContent(identifier: VersionedIdentifier, content: String) {
  private def writeObject(out: java.io.ObjectOutputStream): Unit = {
    ???
  }

  private def readObject(in: java.io.ObjectInputStream): Unit = {
    ???
  }
}