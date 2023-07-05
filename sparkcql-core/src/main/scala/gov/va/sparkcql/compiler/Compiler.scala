package gov.va.sparkcql.compiler

import scala.collection.JavaConverters._

import scala.collection.immutable.Map
import scala.collection.mutable.{HashMap, MutableList}
import gov.va.sparkcql.types._
import org.hl7.elm.r1.{Library, VersionedIdentifier}
import gov.va.sparkcql.adapter.library.{LibraryAdapter, LibraryAdapterAggregator}

/**
  * Three scopes are searched when resolving library references (in order of precedence):
  *  1. Call scope
  *  2. Adapter scope
  *
  * @param libraryAdapters
  */
class Compiler(libraryAdapters: List[LibraryAdapter]) {

  val libraryAggregate = new LibraryAdapterAggregator(libraryAdapters)

  /**
    * Builds a set of call-scoped CQL libraries based on its CQL text alone. The CQL scripts can reference 
    * each other through [[https://cql.hl7.org/02-authorsguide.html#libraries Include Declarations]]
    * or reference adapter-scoped libraries set in the class constructor. Call-scoped libraries can contain
    * fragments are remain anonymous (no [[https://cql.hl7.org/02-authorsguide.html#library Library Declaration required]]).
    *
    * @param callScopedLibraryContents
    * @return
    */
  def compile(callScopedLibraryContents: List[String]): List[Library] = {
    compileExec(callScopedLibraryContents)
  }

  /**
    * Builds a set of CQL libraries using their Identifiers.
    *
    * @param libraryIdentifiers
    * @return
    */
  def compile(libraryIdentifiers: Seq[VersionedIdentifier]): List[Library] = { 
    val callScopedLibraries = libraryIdentifiers.map(libraryFromId(_, None).get).toList
    compileExec(callScopedLibraries)
  }

  protected def compileExec(callScopedLibraryContents: List[String]): List[Library] = {

    // Identify call scoped libraries
    val callScopedLibraries = callScopedLibraryContents.flatMap(content => {
      var identifier = CompilerGateway.parseVersionedIdentifier(content)
      if (identifier.getId() == null) {
        identifier.setId("Anonymous-" + java.util.UUID.randomUUID.toString)
      }
      Map(identifier -> content)
    }).toMap

    // Compile each CQL
    val results = callScopedLibraries.map(f => compileExecOne(f._2, callScopedLibraries))

    // Collect any errors
    var elmErrors = results.toSeq.flatMap(l => {
      l.getAnnotation().asScala.toSeq.map(f => f match {
        case e: org.hl7.cql_annotations.r1.CqlToElmError => 
          s"${e.getErrorType()} ${e.getErrorSeverity()}: ${e.getMessage()}:" +
          s"at <library ${e.getLibraryId()} version '${e.getLibraryVersion()}'>:${e.getStartLine()}:${e.getStartChar()}"
        case e => ""
      }).filter(_.length() > 0)
    })
    
    // Verify no duplicates exist between libraries within call scope. Cannot include adapter
    // scoped since it contains many more most of which aren't relevant to this compilation.
    val batchErrors = callScopedLibraries
      .groupBy(_._1)
      .map(f => (f._1, f._2.size))
      .filter(f => f._2 > 1)
      .map(f => s"Duplicate CQL library detected ${f._1.toString()}")

    results.toList
  }

  protected def compileExecOne(libraryContent: String, callScopedLibraries: Map[VersionedIdentifier, String]): org.hl7.elm.r1.Library = {
    CompilerGateway.compile(
      libraryContent,
      Some(id => libraryFromId(id, Some(callScopedLibraries)).get))
  }

  protected def libraryFromId(identifier: VersionedIdentifier, callScopedLibraries: Option[Map[VersionedIdentifier, String]]): Option[String] = {
    val callScopedLibraryContent = callScopedLibraries.flatMap(_.get(identifier))

    if (callScopedLibraryContent.isDefined) {
      callScopedLibraryContent
    } else {
      val adapterScopedLibraryContent = libraryAggregate.getLibraryContent(identifier)
      if (adapterScopedLibraryContent.isDefined) {
        adapterScopedLibraryContent
      } else {
        throw new Exception(s"Unable to find library ${identifier.toString()}")
      }
    }
  }  
}