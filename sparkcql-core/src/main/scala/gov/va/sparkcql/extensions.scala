package gov.va.sparkcql

import org.hl7.elm.r1.Library
import java.io.StringWriter
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import gov.va.sparkcql.model.LibraryId

// TODO: Consider dropping implicits here

object extensions {
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

  implicit class CqlToElmErrorExt(error: org.hl7.cql_annotations.r1.CqlToElmError) {
    def toPrettyString(): String = {
      val identifier = new org.hl7.elm.r1.VersionedIdentifier()
        .withId(error.getLibraryId())
        .withSystem(error.getLibrarySystem)
        .withVersion(error.getLibraryVersion())
      s"${error.getErrorType()} ${error.getErrorSeverity()}: ${error.getMessage()}:" +
      s"at ${identifier.toPrettyString()}:${error.getStartLine()}:${error.getStartChar()}"
    }
  }

  implicit class VersionedIdentifierExt(identifier: org.hl7.elm.r1.VersionedIdentifier) {
    def toPrettyString(): String = {
      if (identifier.getVersion() != null)
        s"""<library ${identifier.getId()} version '${identifier.getVersion()}'>"""
      else
        s"""<library ${identifier.getId()}>"""
    }
  }  
}