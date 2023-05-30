package gov.va.sparkcql.translation

import java.io.IOException
import java.util._
import collection.JavaConverters._

import org.cqframework.cql.cql2elm.CqlTranslator
import org.cqframework.cql.cql2elm.CqlTranslatorOptions
import org.cqframework.cql.cql2elm.LibraryManager
import org.cqframework.cql.cql2elm.ModelManager
import org.fhir.ucum.UcumEssenceService
import org.hl7.elm.r1.Library
import org.hl7.elm.r1.VersionedIdentifier
import org.hl7.cql_annotations.r1.CqlToElmError
import org.cqframework.cql.cql2elm.LibrarySourceProvider
import scala.collection.mutable.HashMap
import scala.collection.immutable.Map
import org.cqframework.cql.cql2elm.LibraryContentType
import java.nio.charset.StandardCharsets

import gov.va.sparkcql.Extensions._

class CqlToElmBuild(protected val sourceProviders: Option[Seq[LibrarySourceProvider]] = None) {

  val translatorOptions = new CqlTranslatorOptions()
  translatorOptions.setOptions(
    CqlTranslatorOptions.Options.EnableDateRangeOptimization,
    CqlTranslatorOptions.Options.EnableAnnotations,
    CqlTranslatorOptions.Options.EnableLocators,
    CqlTranslatorOptions.Options.EnableResultTypes,
    CqlTranslatorOptions.Options.DisableListDemotion,
    CqlTranslatorOptions.Options.DisableListPromotion,
    CqlTranslatorOptions.Options.DisableMethodInvocation)

  protected def runBuild(targetLibraries: HashMap[VersionedIdentifier, String]): Seq[Library] = {
    val modelManager = new ModelManager()
    val libraryManager = new LibraryManager(modelManager)
    val ucumService = new UcumEssenceService(classOf[UcumEssenceService].getResourceAsStream("/ucum-essence.xml"))

    val targetLibrariesSourceProvider = new InMemoryLibrarySourceProvider(targetLibraries.asJava)
    libraryManager.getLibrarySourceLoader().registerProvider(targetLibrariesSourceProvider)

    sourceProviders.foreach(_.foreach(libraryManager.getLibrarySourceLoader().registerProvider(_)))
    
    targetLibraries.map(library => {
      val result = CqlGateway.compile(library._2, modelManager, libraryManager, ucumService, translatorOptions)
      result.getAnnotation().forEach(f => {
        if (f.isInstanceOf[CqlToElmError]) throw new Exception(f.asInstanceOf[CqlToElmError].toPrettyString())
      })
      result
    }).toSeq
  }

  def build(libraryIdentifiers: Array[VersionedIdentifier]): Seq[Library] = {
    val targetLibraries = new HashMap[VersionedIdentifier, String]()
    libraryIdentifiers.foreach(id => {
      sourceProviders.get.foreach(provider => {
        if (provider.isLibraryContentAvailable(id, LibraryContentType.CQL)) {
          targetLibraries(id) = new String(provider.getLibraryContent(id, LibraryContentType.CQL).readAllBytes(), StandardCharsets.UTF_8)
        }
      })
    })
  
    runBuild(targetLibraries)
  }

  def build(cqlText: Array[String]): Seq[Library] = {
    val targetLibraries = HashMap[VersionedIdentifier, String]()
    cqlText.foreach(cql => {
      val id = CqlGateway.parseVersionedIdentifier(cql)
      // Duplicate anonmymous libraries are allowed
      if (id.getId() == null) {
        id.setId("Anonymous-" + java.util.UUID.randomUUID.toString)
      } else if (targetLibraries.isDefinedAt(id)) {
        throw new Exception(s"Duplicate CQL library detected ${id.toPrettyString()}")
      }
      targetLibraries.put(id, cql)
    })

    runBuild(targetLibraries)
  }

  def build(cqlText: String*): Seq[Library] = {
    build(cqlText.toArray)
  }
}