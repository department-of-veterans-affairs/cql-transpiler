package gov.va.sparkcql.converter

import gov.va.sparkcql.dataprovider.FileContent
import gov.va.sparkcql.compiler.CqlCompilerGateway
import gov.va.sparkcql.model.{VersionedIdentifier, LibraryData}

class FileContentToLibraryData extends Convertable[FileContent, LibraryData] {

  override def convert(source: FileContent): LibraryData = {
    source match {
      case content: FileContent => 
        val id = VersionedIdentifier(CqlCompilerGateway.parseVersionedIdentifier(content.value))
        new LibraryData(id, content.value)
      case _ => throw new Exception("Unable to convert type " + source.getClass().getSimpleName())
    }
  }
}

// TODO: DELETE