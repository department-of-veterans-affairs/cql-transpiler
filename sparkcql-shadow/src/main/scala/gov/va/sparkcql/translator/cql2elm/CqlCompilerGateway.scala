package gov.va.sparkcql.translator.cql2elm

import org.cqframework.cql.cql2elm.CqlTranslator
import org.cqframework.cql.cql2elm.CqlTranslatorOptions
import org.cqframework.cql.cql2elm.LibraryManager
import org.cqframework.cql.cql2elm.ModelManager
import org.fhir.ucum.UcumEssenceService
import org.hl7.elm.r1.Library
import org.hl7.elm.r1.VersionedIdentifier
import org.cqframework.cql.gen.cqlLexer
import org.antlr.v4.runtime.ANTLRInputStream
import java.io.InputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.cqframework.cql.gen.cqlParser

/**
  * Compiles a set of libraries from CQL to ELM using CQF's CqlTranslator. The sole purpose
  * of this package is to shade all transitive dependencies to avoid issues between
  * CQF and Spark which both use differing and incompatible versions of ANTLR. Avoid introducing
  * parameters which use a type defined in a package which also defines a transitive dependency 
  * to ANTLR. For instance, anything in info.cqframework:cql-to-elm.
  */
object CqlCompilerGateway {

  def compile(cqlText: String, libraryResolver: Option[(VersionedIdentifier) => String]): Library = {
    val ucumService = new UcumEssenceService(classOf[UcumEssenceService].getResourceAsStream("/ucum-essence.xml"))
    val modelManager = new ModelManager()
    val libraryManager = new LibraryManager(modelManager)
    if (libraryResolver.isDefined) {
      libraryManager.getLibrarySourceLoader().registerProvider(new LibraryDataBridgeProvider(libraryResolver.get))
    }
    
    val translatorOptions = new CqlTranslatorOptions()
    translatorOptions.setOptions(
      CqlTranslatorOptions.Options.EnableDateRangeOptimization,
      CqlTranslatorOptions.Options.EnableResultTypes,
      )

    var translator = CqlTranslator.fromText(cqlText, modelManager, libraryManager, ucumService, translatorOptions)
    translator.toELM()
  }

  def parseVersionedIdentifier(cqlText: String): VersionedIdentifier = {
    // TODO: Find a more efficient way to parse the identifier without compiling everything.
    compile(cqlText, None).getIdentifier()
  }
}