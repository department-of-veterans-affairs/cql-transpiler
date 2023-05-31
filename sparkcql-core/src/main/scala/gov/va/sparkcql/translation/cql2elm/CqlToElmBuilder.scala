package gov.va.sparkcql.translation.cql2elm

import java.io.IOException
import java.util._
import collection.JavaConverters._

import org.hl7.elm.r1.Library
import org.hl7.elm.r1.VersionedIdentifier
import org.hl7.cql_annotations.r1.CqlToElmError
import scala.collection.immutable.Map
import scala.collection.mutable.HashMap
import gov.va.sparkcql.dataprovider.library.LibraryDataProvider
import gov.va.sparkcql.translation.CqlCompilerGateway
import gov.va.sparkcql.common.Extensions._

class CqlToElmBuilder(protected val transitiveLibraries: Option[LibraryDataProvider] = None) {

  protected def runBuild(directLibraries: Map[VersionedIdentifier, String]): Seq[Library] = {
    directLibraries.map(library => {
      var result = CqlCompilerGateway.compile(library._2, Some(id => directLibraries(id)))
      result.getAnnotation().forEach(f => {
        if (f.isInstanceOf[CqlToElmError]) throw new Exception(f.asInstanceOf[CqlToElmError].toPrettyString())
      })
      result
    }).toSeq
  }

  /**
    * Builds a set of libraries based on their VersionedIdentifiers
    *
    * @param directLibraryIdentifiers
    * @return
    */
  def build(directLibraryIdentifiers: Array[VersionedIdentifier]): Seq[Library] = {
    val libraries = directLibraryIdentifiers.flatMap(
      id => transitiveLibraries.map(
        provider => (id -> provider.getOrElse(id, throw new Exception(s"${id.toPrettyString()} not found.")))
      )
    ).toMap

    runBuild(libraries)
  }

  def build(cqlText: Array[String]): Seq[Library] = {
    val libraries = HashMap[VersionedIdentifier, String]()
    cqlText.foreach(cql => {
      val id = CqlCompilerGateway.parseVersionedIdentifier(cql)
      if (id.getId() == null) {
        // Duplicate anonmymous libraries are allowed
        id.setId("Anonymous-" + java.util.UUID.randomUUID.toString)
      } else if (libraries.isDefinedAt(id)) {
        throw new Exception(s"Duplicate CQL library detected ${id.toPrettyString()}")
      }
      libraries.put(id, cql)
    })

    runBuild(libraries.toMap)
  }

  def build(cqlText: String*): Seq[Library] = {
    build(cqlText.toArray)
  }
}