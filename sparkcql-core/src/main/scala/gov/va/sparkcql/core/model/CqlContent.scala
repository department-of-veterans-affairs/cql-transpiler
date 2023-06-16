package gov.va.sparkcql.core.model

case class CqlContent(identifier: VersionedId, content: String) {
  private def writeObject(out: java.io.ObjectOutputStream): Unit = {
    ???
  }

  private def readObject(in: java.io.ObjectInputStream): Unit = {
    ???
  }
}