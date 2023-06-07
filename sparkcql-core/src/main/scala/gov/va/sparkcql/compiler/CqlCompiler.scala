package gov.va.sparkcql.compiler

import scala.collection.JavaConverters._
import scala.collection.JavaConverters._

import scala.collection.immutable.Map
import scala.collection.mutable.{HashMap, MutableList}
import gov.va.sparkcql.dataprovider.DataProvider
import gov.va.sparkcql.model.{LibraryData, VersionedIdentifier}
import org.apache.spark.sql.SparkSession

// TODO: Return all errors instead of throwing them.
// TODO: General quality improvement

/**
  * Three scopes are searched when resolving library references (in order of precedence):
  *  1. Call scope
  *  2. Provider scope
  *
  * @param libraryProvider
  */
class CqlCompiler(providerScopedLibraries: Option[DataProvider], spark: Option[SparkSession]) {

  def this() {
    this(None, None)
  }
  
  /**
    * Builds a set of call-scoped CQL libraries based on its CQL text alone. The CQL scripts can reference 
    * each other through [[https://cql.hl7.org/02-authorsguide.html#libraries Include Declarations]]
    * or reference provider-scoped libraries set in the class constructor. Call-scoped libraries can contain
    * fragments are remain anonymous (no [[https://cql.hl7.org/02-authorsguide.html#library Library Declaration required]]).
    *
    * @param libraryContents
    * @return
    */
  def compile(libraryContents: List[String]): CqlCompilation = {
    compileExec(libraryContents)
  }

  /**
    * Builds a set of CQL libraries using their VersionedIdentifiers.
    *
    * @param libraryIdentifiers
    * @return
    */
  def compile(libraryIdentifiers: Seq[VersionedIdentifier]): CqlCompilation = { 
    val callScopedLibraries = libraryIdentifiers.map(libraryDataFromId(_, None).get.content).toList
    compileExec(callScopedLibraries)
  }

  protected def compileExec(libraryContents: List[String]): CqlCompilation = {

    // Convert CQL text to LibraryData to map a VersionedIdentifier (VersionedIdentifier) to CQL
    val callScopedLibraries = libraryContents.map(content => {
      var elmId = CqlCompilerGateway.parseVersionedIdentifier(content)
      if (elmId.getId() == null) {
        elmId.setId("Anonymous-" + java.util.UUID.randomUUID.toString)
      }
      LibraryData(VersionedIdentifier(elmId), content)
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
    
    // Verify no duplicates exist between libraries within call scope. Cannot include provider
    // scoped since it contains many more most of which aren't relevant to this compilation.
    val batchErrors = callScopedLibraries
      .groupBy(_.identifier)
      .map(f => (f._1, f._2.length))
      .filter(f => f._2 > 1)
      .map(f => s"Duplicate CQL library detected ${f._1.toString()}")
      .toSeq

    CqlCompilation(results, elmErrors ++ batchErrors)
  }

  protected def compileExecOne(libraryContent: String, callScopedLibraries: Seq[LibraryData]): org.hl7.elm.r1.Library = {
    CqlCompilerGateway.compile(
      libraryContent,
      Some(id => libraryDataFromId(VersionedIdentifier(id), Some(callScopedLibraries)).get.content))
  }

  protected def libraryDataFromId(identifier: VersionedIdentifier, callScopedLibraries: Option[Seq[LibraryData]]): Option[LibraryData] = {
    if (callScopedLibraries.isDefined) {
      val callScoped = callScopedLibraries.get.filter(p => p.identifier == identifier)
      if (callScoped.length > 0) return Some(callScoped.head)
    }

    if (providerScopedLibraries.isDefined) {
      val providerScoped = providerScopedLibraries.get.fetch[LibraryData](spark.get)
      val providerResults = providerScoped.filter(f => f.identifier == identifier)
      if (!providerResults.isEmpty) return Some(providerResults.head())
    }

    throw new Exception(s"Unable to find library ${identifier.toString()}")
  }  
}