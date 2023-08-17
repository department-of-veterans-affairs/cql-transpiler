package gov.va.sparkcql.service.compiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.CqlTranslatorOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.cqframework.cql.cql2elm.ModelManager;
import org.fhir.ucum.UcumEssenceService;
import org.fhir.ucum.UcumException;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

import com.google.inject.Inject;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.repository.cql.CqlSourceRepository;

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
        this.inScopeCqlSources = this.cqlSourceRepository.findMany(cqlIdentifier);
        return compileIdentifiedLibraries();
    }

    private List<Library> compileIdentifiedLibraries() {
        // The LibrarySourceProvider interface allows integration with CQF. However,
        // the implementation must be hidden away since our component is shaded.
        LibrarySourceProvider librarySourceProvider = new CqfLibrarySourceProvider(this.inScopeCqlSources, this.cqlSourceRepository);

        return inScopeCqlSources.stream().map(cs -> execCompile(cs.getSource(), librarySourceProvider)).toList();
    }

    private Library execCompile(String cqlText, LibrarySourceProvider librarySourceProvider) {
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

    static class CqfLibrarySourceProvider implements LibrarySourceProvider {

        protected List<CqlSource> inScopeCqlSources;
        protected CqlSourceRepository cqlSourceRepository;
    
        public CqfLibrarySourceProvider(List<CqlSource> inScopeCqlSources, CqlSourceRepository cqlSourceRepository) {
            this.inScopeCqlSources = inScopeCqlSources;
            this.cqlSourceRepository = cqlSourceRepository;
        }
    
        @Override
        public InputStream getLibrarySource(VersionedIdentifier libraryIdentifier) {
            var lookup = inScopeCqlSources.stream().filter(cs -> cs.getIdentifier().equals(libraryIdentifier)).findFirst();
            if (lookup.isEmpty()) {
                var cs = this.cqlSourceRepository.findOne(libraryIdentifier);
                this.inScopeCqlSources.add(cs);
                lookup = Optional.of(cs);
            }
    
            return new ByteArrayInputStream(lookup.get().getSource().getBytes());
        }
    }    
}