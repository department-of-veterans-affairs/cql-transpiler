package gov.va.sparkcql.compiler;

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

import gov.va.sparkcql.model.CqlSource;
import gov.va.sparkcql.repository.CqlSourceRepository;

public class CqfCompiler implements Compiler, LibrarySourceProvider {

    protected List<CqlSource> inScopeCqlSources;
    protected CqlSourceRepository cqlSourceRepository;

    public CqfCompiler() {
    }
    
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
        return inScopeCqlSources.stream().map(cs -> execCompile(cs.getSource(), this)).toList();
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

    @Override
    public InputStream getLibrarySource(org.hl7.elm.r1.VersionedIdentifier libraryIdentifier) {
        var lookup = inScopeCqlSources.stream().filter(cs -> cs.getIdentifier().equals(libraryIdentifier)).findFirst();
        if (lookup.isEmpty()) {
            var cs = this.cqlSourceRepository.findOne(libraryIdentifier);
            this.inScopeCqlSources.add(cs);
            lookup = Optional.of(cs);
        }

        return new ByteArrayInputStream(lookup.get().getSource().getBytes());
    }
}
