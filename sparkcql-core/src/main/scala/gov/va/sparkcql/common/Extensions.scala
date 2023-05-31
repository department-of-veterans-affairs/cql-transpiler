package gov.va.sparkcql.common

import org.hl7.elm.r1.Library
import java.io.StringWriter
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import org.hl7.cql_annotations.r1.CqlToElmError
import org.hl7.elm.r1.VersionedIdentifier

object Extensions {
  implicit class CqlLibraryExt(library: Library) {
    def toJson(): String = {
      val writer = new StringWriter()
      ElmLibraryWriterFactory.getWriter("text/cql").write(library, writer)
      writer.getBuffer().toString()
    }

    def show(): Unit = {
      // TODO: Add formatting
      println(library.toJson())
    }
  }

  implicit class CqlToElmErrorExt(error: CqlToElmError) {
    def toPrettyString(): String = {
      val id = new VersionedIdentifier().withId(error.getLibraryId()).withVersion(error.getLibraryVersion())
      s"${error.getErrorType()} ${error.getErrorSeverity()}: ${error.getMessage()}:" +
      s"at ${id.toPrettyString()}:${error.getStartLine()}:${error.getStartChar()}"
    }
  }

  implicit class VersionedIdentifierExt(id: VersionedIdentifier) {
    def toPrettyString(): String = {
      if (id.getVersion() != null)
        s"""<library ${id.getId()} version '${id.getVersion()}'>"""
      else
        s"""<library ${id.getId()}>"""
    }
  }  
}