package gov.va.sparkcql.translation.cql2elm

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
import gov.va.sparkcql.adapter.library.{LibraryDataAdapter, InMemoryLibraryDataAdapter}
import gov.va.sparkcql.translation.CqlGateway
import gov.va.sparkcql.common.Extensions._

class CqlToElmBuilder(protected val libaryDataAdapters: Option[Seq[LibraryDataAdapter]] = None) {

  val translatorOptions = new CqlTranslatorOptions()
  translatorOptions.setOptions(
    CqlTranslatorOptions.Options.EnableDateRangeOptimization,
    CqlTranslatorOptions.Options.EnableAnnotations,
    CqlTranslatorOptions.Options.EnableLocators,
    CqlTranslatorOptions.Options.EnableResultTypes,
    CqlTranslatorOptions.Options.DisableListDemotion,
    CqlTranslatorOptions.Options.DisableListPromotion,
    CqlTranslatorOptions.Options.DisableMethodInvocation)

  protected def runBuild(libraries: HashMap[VersionedIdentifier, String]): Seq[Library] = {
    val modelManager = new ModelManager()
    val libraryManager = new LibraryManager(modelManager)
    val ucumService = new UcumEssenceService(classOf[UcumEssenceService].getResourceAsStream("/ucum-essence.xml"))

    val librariesAdapter = new InMemoryLibraryDataAdapter(libraries.toMap)
    libraryManager.getLibrarySourceLoader().registerProvider(librariesAdapter)

    libaryDataAdapters.foreach(_.foreach(libraryManager.getLibrarySourceLoader().registerProvider(_)))
    
    libraries.map(library => {
      val result = CqlGateway.compile(library._2, modelManager, libraryManager, ucumService, translatorOptions)
      result.getAnnotation().forEach(f => {
        if (f.isInstanceOf[CqlToElmError]) throw new Exception(f.asInstanceOf[CqlToElmError].toPrettyString())
      })
      result
    }).toSeq
  }

  def build(libraryIdentifiers: Array[VersionedIdentifier]): Seq[Library] = {
    val libraries = new HashMap[VersionedIdentifier, String]()
    libraryIdentifiers.foreach(id => {
      libaryDataAdapters.get.foreach(provider => {
        if (provider.isLibraryContentAvailable(id, LibraryContentType.CQL)) {
          libraries(id) = new String(provider.getLibraryContent(id, LibraryContentType.CQL).readAllBytes(), StandardCharsets.UTF_8)
        }
      })
    })
  
    runBuild(libraries)
  }

  def build(cqlText: Array[String]): Seq[Library] = {
    val libraries = HashMap[VersionedIdentifier, String]()
    cqlText.foreach(cql => {
      val id = CqlGateway.parseVersionedIdentifier(cql)
      // Duplicate anonmymous libraries are allowed
      if (id.getId() == null) {
        id.setId("Anonymous-" + java.util.UUID.randomUUID.toString)
      } else if (libraries.isDefinedAt(id)) {
        throw new Exception(s"Duplicate CQL library detected ${id.toPrettyString()}")
      }
      libraries.put(id, cql)
    })

    runBuild(libraries)
  }

  def build(cqlText: String*): Seq[Library] = {
    build(cqlText.toArray)
  }
}