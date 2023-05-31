package gov.va.sparkcql.adapter.library

import java.io.{InputStream, ByteArrayInputStream}
import gov.va.sparkcql.common.Files
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.translation.CqlGateway

class FileLibraryDataAdapter(path: String) extends LibraryDataAdapter {

  val files: Array[String] = Files.search(path, ".cql")
  val libraries = files.map(f => {
    val content = scala.io.Source.fromFile(f).mkString
    val id = CqlGateway.parseVersionedIdentifier(content)
    (id -> content)
  }).toMap
  
  override def getLibrarySource(libraryIdentifier: VersionedIdentifier): InputStream = {
    if (libraries.isDefinedAt(libraryIdentifier)) {
      val content = libraries.get(libraryIdentifier).get
      new ByteArrayInputStream(content.getBytes(java.nio.charset.StandardCharsets.UTF_8.name))
    } else {
      null
    }
  }
}