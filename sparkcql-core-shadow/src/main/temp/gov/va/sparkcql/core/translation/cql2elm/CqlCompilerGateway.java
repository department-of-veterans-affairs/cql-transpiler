package gov.va.sparkcql.core.translator.cql2elm;

import java.util.Optional;
import java.util.function.Function;

import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.CqlTranslatorOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.fhir.ucum.UcumEssenceService;
import org.fhir.ucum.UcumException;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

/**
 * Compiles a set of libraries from CQL to ELM using CQF's CqlTranslator. The
 * sole purpose
 * of this package is to shade all transitive dependencies to avoid issues
 * between
 * CQF and Spark which both use differing and incompatible versions of ANTLR.
 * Avoid introducing
 * parameters which use a type defined in a package which also defines a
 * transitive dependency
 * to ANTLR. For instance, anything in info.cqframework:cql-to-elm.
 */
public final class CqlCompilerGateway {

    private CqlCompilerGateway() {
    }

    public static Library compile(String cqlText, Optional<Function<VersionedIdentifier, String>> libraryResolver) {
        UcumEssenceService ucumService = null;
        try {
            ucumService = new UcumEssenceService(UcumEssenceService.class.getResourceAsStream("/ucum-essence.xml"));
        } catch (UcumException e) {
            e.printStackTrace();
        }
        var modelManager = new ModelManager();
        var libraryManager = new LibraryManager(modelManager);
        if (libraryResolver.isPresent()) {
            libraryManager.getLibrarySourceLoader()
                    .registerProvider(new LibraryDataBridgeProvider(libraryResolver.get()));
        }

        var translatorOptions = new CqlTranslatorOptions();
        translatorOptions.setOptions(
                CqlTranslatorOptions.Options.EnableDateRangeOptimization,
                CqlTranslatorOptions.Options.EnableAnnotations,
                CqlTranslatorOptions.Options.EnableLocators,
                CqlTranslatorOptions.Options.EnableResultTypes,
                CqlTranslatorOptions.Options.DisableListDemotion,
                CqlTranslatorOptions.Options.DisableListPromotion,
                CqlTranslatorOptions.Options.DisableMethodInvocation);

        var translator = CqlTranslator.fromText(cqlText, modelManager, libraryManager, ucumService, translatorOptions);
        return translator.toELM();
    }

    public static VersionedIdentifier parseVersionedIdentifier(String cqlText) {
        // TODO: Find a more efficient way to parse the identifier without compiling
        // everything.
        return compile(cqlText, Optional.empty()).getIdentifier();
    }
}