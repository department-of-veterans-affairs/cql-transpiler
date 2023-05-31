package gov.va.sparkcql.translation

//import java.util._

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
  * CQF and Spark which both use differing and incompatible versions of ANTLR.
  */
object CqlGateway {
  def compile(cqlText: String, modelManager: ModelManager, libraryManager: LibraryManager, ucumService: UcumEssenceService, options: CqlTranslatorOptions): Library = {
    var translator = CqlTranslator.fromText(cqlText, modelManager, libraryManager, ucumService, options)
    translator.toELM()
  }

  def parseVersionedIdentifier(cqlText: String): VersionedIdentifier = {
    // TODO: Find a more efficient way to parse the identifier without compiling everything (though probably not a big deal)
    val modelManager = new ModelManager()
    val libraryManager = new LibraryManager(modelManager)
    val ucumService = new UcumEssenceService(classOf[UcumEssenceService].getResourceAsStream("/ucum-essence.xml"))
    val options = new CqlTranslatorOptions()
    var translator = CqlTranslator.fromText(cqlText, modelManager, libraryManager, ucumService, options)
    translator.toELM().getIdentifier()
  }
}