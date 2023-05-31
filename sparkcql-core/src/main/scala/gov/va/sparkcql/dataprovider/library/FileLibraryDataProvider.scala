package gov.va.sparkcql.dataprovider.library

import java.io.{InputStream, ByteArrayInputStream}
import gov.va.sparkcql.common.Files
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.translation.CqlCompilerGateway

class FileLibraryDataProvider(path: String) extends InMemoryLibraryDataProvider(FileLibraryDataProvider.prefetch(path))

object FileLibraryDataProvider {
  
  def prefetch(path: String) = {
    Files.search(path, ".cql").map(f => {
      val content = scala.io.Source.fromFile(f).mkString
      val id = CqlCompilerGateway.parseVersionedIdentifier(content)
      (id -> content)
    }).toMap
  }
}