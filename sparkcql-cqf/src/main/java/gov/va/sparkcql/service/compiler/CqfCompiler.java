package gov.va.sparkcql.service.compiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.fhir.ucum.UcumEssenceService;
import org.fhir.ucum.UcumException;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.CqlTranslatorOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.cqframework.cql.cql2elm.ModelManager;

import com.google.inject.Inject;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.pipeline.compiler.Compiler;
import gov.va.sparkcql.pipeline.compiler.CqlParser;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;

public class CqfCompiler implements Compiler {

    protected List<CqlSource> inScopeCqlSources;
    protected CqlSourceRepository cqlSourceRepository;

    @Inject
    public CqfCompiler(CqlSourceRepository cqlSourceRepository) {
        this.cqlSourceRepository = cqlSourceRepository;
    }

    public List<Library> compile(String... cqlText) {
        this.inScopeCqlSources = Stream.of(cqlText)
            .map(text -> {
                return new CqlSource()
                    .withIdentifier(new CqlParser().parseVersionedIdentifier(text))
                    .withSource(text);
            }).toList();

        return compileIdentifiedLibraries();
    }

    public List<Library> compile(List<VersionedIdentifier> cqlIdentifier) {
        this.inScopeCqlSources = this.cqlSourceRepository.readById(cqlIdentifier);
        return compileIdentifiedLibraries();
    }

    private List<Library> compileIdentifiedLibraries() {
        return inScopeCqlSources.stream().map(cs -> execCompile(cs.getSource())).toList();
    }

    private Library execCompile(String cqlText) {
        LibrarySourceProvider librarySourceProvider = new CqfLibrarySourceProvider(this.inScopeCqlSources, this.cqlSourceRepository);
        UcumEssenceService ucumService = null;
        try {
            ucumService = new UcumEssenceService(UcumEssenceService.class.getResourceAsStream("/ucum-essence.xml"));
        } catch (UcumException e) {
            // Default to no service
        }
        var modelManager = new ModelManager();
        var libraryManager = new LibraryManager(modelManager);        
        libraryManager.getLibrarySourceLoader().registerProvider(librarySourceProvider);

        var translatorOptions = new CqlTranslatorOptions();
        translatorOptions.setOptions();

        var translator = CqlTranslator.fromText(cqlText, modelManager, libraryManager, ucumService, translatorOptions);
        var elm = translator.toELM();

        // If there are any errors, throw them now. Prefer loud error reporting over quiet failures.
        var errorChecker = new CqlErrorChecker(elm);
        if (errorChecker.hasErrors()) {
            System.out.println(errorChecker.toPrettyString());
        }

        return elm;
    }

    private static class CqfLibrarySourceProvider implements LibrarySourceProvider {

        protected List<CqlSource> inScopeCqlSources;
        protected CqlSourceRepository cqlSourceRepository;
    
        CqfLibrarySourceProvider(List<CqlSource> inScopeCqlSources, CqlSourceRepository cqlSourceRepository) {
            this.inScopeCqlSources = inScopeCqlSources;
            this.cqlSourceRepository = cqlSourceRepository;
        }
    
        @Override
        public InputStream getLibrarySource(VersionedIdentifier libraryIdentifier) {
            var lookup = inScopeCqlSources.stream().filter(cs -> cs.getIdentifier().equals(libraryIdentifier)).findFirst();
            if (lookup.isEmpty()) {
                var cs = this.cqlSourceRepository.readById(libraryIdentifier);
                this.inScopeCqlSources.add(cs);
                lookup = Optional.of(cs);
            }
    
            return new ByteArrayInputStream(lookup.get().getSource().getBytes());
        }
    }    
}