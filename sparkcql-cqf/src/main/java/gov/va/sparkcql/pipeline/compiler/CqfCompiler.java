package gov.va.sparkcql.pipeline.compiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.cqframework.cql.cql2elm.*;
import org.fhir.ucum.UcumEssenceService;
import org.fhir.ucum.UcumException;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;

public class CqfCompiler implements Compiler {

    protected List<CqlSource> inScopeCqlSources;
    protected CqlSourceRepository cqlSourceRepository;

    public CqfCompiler(CqlSourceRepository cqlSourceRepository) {
        this.cqlSourceRepository = cqlSourceRepository;
    }

    public Plan compile(String... cqlText) {
        this.inScopeCqlSources = Stream.of(cqlText)
            .map(text -> {
                return new CqlSource()
                    .withIdentifier(new QualifiedIdentifier(new CqlParser().parseVersionedIdentifier(text)))
                    .withSource(text);
            }).collect(Collectors.toList());

        return new Plan().withLibraries(compileIdentifiedLibraries());
    }

    public Plan compile(List<VersionedIdentifier> cqlIdentifier) {
        this.inScopeCqlSources = this.cqlSourceRepository.readById(cqlIdentifier);
        return new Plan().withLibraries(compileIdentifiedLibraries());
    }

    private List<Library> compileIdentifiedLibraries() {
        return inScopeCqlSources.stream().map(cs -> execCompile(cs.getSource())).collect(Collectors.toList());
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

        var translator = CqlTranslator.fromText(cqlText, libraryManager);
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
                lookup = Optional.ofNullable(cs);
            }
    
            return new ByteArrayInputStream(lookup.get().getSource().getBytes());
        }
    }    
}