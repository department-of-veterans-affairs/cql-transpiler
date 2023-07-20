package gov.va.sparkcql.compiler;

import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.CqlTranslatorOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.cqframework.cql.cql2elm.ModelManager;
import org.fhir.ucum.UcumEssenceService;
import org.fhir.ucum.UcumException;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

/**
 * Compiles a set of libraries from CQL to ELM using CQF's CqlTranslator. The
 * sole purpose of this package is to shade all transitive dependencies to avoid
 * issues between CQF and Spark which both use differing and incompatible
 * versions of ANTLR. Avoid introducing parameters which use a type defined in a
 * package which also defines a transitive dependency to ANTLR. For instance,
 * anything in info.cqframework:cql-to-elm.
 */
public class CompilerGateway {

    public Library compile(String cqlText, LibrarySourceProvider librarySourceProvider) {
        UcumEssenceService ucumService = null;
        try {
            ucumService = new UcumEssenceService(UcumEssenceService.class.getResourceAsStream("/ucum-essence.xml"));
        } catch (UcumException e) {
            // Default to no service
        }
        var modelManager = new ModelManager();
        var libraryManager = new LibraryManager(modelManager);
        if (librarySourceProvider != null) {
            libraryManager.getLibrarySourceLoader().registerProvider(librarySourceProvider);
        }

        var translatorOptions = new CqlTranslatorOptions();
        translatorOptions.setOptions();

        var translator = CqlTranslator.fromText(cqlText, modelManager, libraryManager, ucumService, translatorOptions);
        return translator.toELM();
    }

    public VersionedIdentifier parseVersionedIdentifier(String cqlText) {
        // TODO: Find a more efficient way to parse the identifier without compiling.
        var identifier = compile(cqlText, null).getIdentifier();
        if (identifier.getId() == null) {
            identifier.setId("Anonymous-" + java.util.UUID.randomUUID().toString());
        }
        return identifier;
    }
}