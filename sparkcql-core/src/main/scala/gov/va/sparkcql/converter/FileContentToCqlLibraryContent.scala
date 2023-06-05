package gov.va.sparkcql.converter

import gov.va.sparkcql.dataprovider.FileContent
import gov.va.sparkcql.translation.CqlCompilerGateway
import gov.va.sparkcql.model.{LibraryId, IdentifiedLibraryContent}

class FileContentToIdentifiedLibraryContent extends Converter {

  override def convert[S, T](source: S): T = {
    source match {
      case content: FileContent => 
        val id = LibraryId(CqlCompilerGateway.parseVersionedIdentifier(content.value))
        new IdentifiedLibraryContent(id, content.value).asInstanceOf[T]
      case _ => throw new Exception("Unable to convert type " + source.getClass().getSimpleName())
    }
  }
}