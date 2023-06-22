package gov.va.sparkcql.core.translator.cql2elm

import scala.collection.JavaConverters._

import scala.collection.immutable.Map
import scala.collection.mutable.{HashMap, MutableList}
import gov.va.sparkcql.core.model.{VersionedId, CqlContent}
import org.hl7.elm.r1.Library
import gov.va.sparkcql.core.source.{Source, SourceAggregator}

/**
  * Three scopes are searched when resolving library references (in order of precedence):
  *  1. Call scope
  *  2. Adapter scope
  *
  * @param libraryAdapter
  */
class CqlToElmTranslator(sources: List[Source]) {

  val sourceAggregate = new SourceAggregator(sources)

  def this() {
    this(List())
  }
  
  /**
    * Builds a set of call-scoped CQL libraries based on its CQL text alone. The CQL scripts can reference 
    * each other through [[https://cql.hl7.org/02-authorsguide.html#libraries Include Declarations]]
    * or reference adapter-scoped libraries set in the class constructor. Call-scoped libraries can contain
    * fragments are remain anonymous (no [[https://cql.hl7.org/02-authorsguide.html#library Library Declaration required]]).
    *
    * @param libraryContents
    * @return
    */
  def translate(libraryContents: List[String]): Seq[Library] = {
    compileExec(libraryContents)
  }

  /**
    * Builds a set of CQL libraries using their VersionedIdentifiers.
    *
    * @param libraryIdentifiers
    * @return
    */
  def translate(libraryIdentifiers: Seq[VersionedId]): Seq[Library] = { 
    val callScopedLibraries = libraryIdentifiers.map(libraryFromId(_, None).get.content).toList
    compileExec(callScopedLibraries)
  }

  protected def compileExec(libraryContents: List[String]): Seq[Library] = {

    // Convert CQL text to CqlContent to map a VersionedId to CQL
    val callScopedLibraries = libraryContents.map(content => {
      var elmId = CqlCompilerGateway.parseVersionedIdentifier(content)
      if (elmId.getId() == null) {
        elmId.setId("Anonymous-" + java.util.UUID.randomUUID.toString)
      }
      CqlContent(VersionedId(elmId), content)
    })

    // Compile each CQL
    val results = callScopedLibraries.map(f => compileExecOne(f.content, callScopedLibraries)).toSeq

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
      .groupBy(_.identifier)
      .map(f => (f._1, f._2.length))
      .filter(f => f._2 > 1)
      .map(f => s"Duplicate CQL library detected ${f._1.toString()}")
      .toSeq

    results
  }

  protected def compileExecOne(libraryContent: String, callScopedLibraries: Seq[CqlContent]): org.hl7.elm.r1.Library = {
    CqlCompilerGateway.compile(
      libraryContent,
      Some(id => libraryFromId(VersionedId(id), Some(callScopedLibraries)).get.content))
  }

  protected def libraryFromId(identifier: VersionedId, callScopedLibraries: Option[Seq[CqlContent]]): Option[CqlContent] = {
    if (callScopedLibraries.isDefined) {
      val callScoped = callScopedLibraries.get.filter(p => p.identifier == identifier)
      if (callScoped.length > 0) return Some(callScoped.head)
    }

    val adapterScoped = sourceAggregate.acquireData[CqlContent]()
    val adapterResults = adapterScoped.get.filter(f => f.identifier == identifier)
    if (!adapterResults.isEmpty) return Some(adapterResults.head())

    throw new Exception(s"Unable to find library ${identifier.toString()}")
  }  
}