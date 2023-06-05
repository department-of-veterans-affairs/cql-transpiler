package gov.va.sparkcql.translation.cql2elm

import scala.collection.JavaConverters._
import scala.collection.JavaConverters._

import scala.collection.immutable.Map
import scala.collection.mutable.{HashMap, MutableList}
import gov.va.sparkcql.dataprovider.DataProvider
import gov.va.sparkcql.translation.CqlCompilerGateway
import gov.va.sparkcql.dataprovider.DataProvider
import gov.va.sparkcql.model.{IdentifiedLibraryContent, LibraryId}
import gov.va.sparkcql.extensions._
import org.apache.spark.sql.SparkSession

// TODO: Return all errors instead of throwing them.
// TODO: General quality improvement

case class CqlToElmOutput(libraries: Seq[org.hl7.elm.r1.Library], errors: Seq[String])

/**
  * Three scopes are searched when resolving library references (in order of precedence):
  *  1. Call scope
  *  2. Provider scope
  *
  * @param libraryProvider
  */
class CqlToElmTranslator() {

  def translate(libraryContents: List[String], providerScopedLibraries: DataProvider, spark: SparkSession): CqlToElmOutput = { 
    execute(libraryContents, Some(providerScopedLibraries), Some(spark))
  }

  def translate(libraryContents: List[String]): CqlToElmOutput = {
    execute(libraryContents, None, None)
  }

  /**
    * Builds a specified set of libraries using their VersionedIdentifiers
    *
    * @param libraryIdentifiers
    * @return
    */
  def translate(libraryIdentifiers: Seq[LibraryId], providerScopedLibraries: DataProvider, spark: SparkSession): CqlToElmOutput = { 
    val callScopedLibraries = libraryIdentifiers.map(lookupLibrary(_, None, Some(providerScopedLibraries), Some(spark)).get.content).toList
    execute(callScopedLibraries, Some(providerScopedLibraries), Some(spark))
  }

  protected def execute(libraryContents: List[String], providerScopedLibraries: Option[DataProvider], spark: Option[SparkSession]): CqlToElmOutput = {

    // Convert CQL text to IdentifiedLibraryContent to map a LibraryId (VersionedIdentifier) to CQL
    val callScopedLibraries = libraryContents.map(content => {
      var elmId = CqlCompilerGateway.parseVersionedIdentifier(content)
      if (elmId.getId() == null) {
        elmId.setId("Anonymous-" + java.util.UUID.randomUUID.toString)
      }
      IdentifiedLibraryContent(LibraryId(elmId), content)
    })

    // Compile each CQL
    val results = callScopedLibraries.map(f => executeOne(f.content, callScopedLibraries, providerScopedLibraries, spark)).toSeq

    // Collect any errors
    var elmErrors = results.toSeq.flatMap(l => {
      l.getAnnotation().asScala.toSeq.map(f => f match {
        case e: org.hl7.cql_annotations.r1.CqlToElmError => e.toPrettyString()
        case e => ""
      }).filter(_.length() > 0)
    })
    
    // Verify no duplicates exist between libraries within call scope. Cannot include provider
    // scoped since it contains many more most of which aren't relevant to this compilation.
    val batchErrors = callScopedLibraries
      .groupBy(_.identifier)
      .map(f => (f._1, f._2.length))
      .filter(f => f._2 > 1)
      .map(f => s"Duplicate CQL library detected ${f._1.toPrettyString()}")
      .toSeq

    CqlToElmOutput(results, elmErrors ++ batchErrors)
  }

  protected def executeOne(libraryContent: String, callScopedLibraries: Seq[IdentifiedLibraryContent], providerScopedLibraries: Option[DataProvider], spark: Option[SparkSession]): org.hl7.elm.r1.Library = {
    CqlCompilerGateway.compile(
      libraryContent,
      Some(id => lookupLibrary(LibraryId(id), Some(callScopedLibraries), providerScopedLibraries, spark).get.content))
  }

  protected def lookupLibrary(identifier: LibraryId, callScopedLibraries: Option[Seq[IdentifiedLibraryContent]], providerScopedLibraries: Option[DataProvider], spark: Option[SparkSession]): Option[IdentifiedLibraryContent] = {
    if (callScopedLibraries.isDefined) {
      val callScoped = callScopedLibraries.get.filter(p => p.identifier == identifier)
      if (callScoped.length > 0) return Some(callScoped.head)
    }

    if (providerScopedLibraries.isDefined) {
      val providerScoped = providerScopedLibraries.get.fetch[IdentifiedLibraryContent](spark.get)
      val providerResults = providerScoped.filter(f => f.identifier == identifier)
      if (!providerResults.isEmpty) return Some(providerResults.head())
    }

    throw new Exception(s"Unable to find library ${identifier.toPrettyString()}")
  }  
}