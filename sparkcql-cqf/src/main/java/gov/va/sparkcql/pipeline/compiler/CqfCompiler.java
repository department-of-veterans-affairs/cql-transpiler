package gov.va.sparkcql.pipeline.compiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.cqframework.cql.cql2elm.*;
import org.fhir.ucum.UcumEssenceService;
import org.fhir.ucum.UcumException;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;

public class CqfCompiler implements Compiler {

    protected List<CqlSource> callScopedCqlSources;
    protected CqlSourceRepository cqlSourceRepository;

    // An included CQL source not specified at the "compile" call site. Will be included as
    // part of the plan.
    protected ArrayList<CqlSource> transitiveDependencies;

    public CqfCompiler(CqlSourceRepository cqlSourceRepository) {
        this.cqlSourceRepository = cqlSourceRepository;
    }

    @Override
    public Plan compile(String... anonymousCql) {
        this.callScopedCqlSources = Stream.of(anonymousCql)
                .map(text -> {
                    return new CqlSource()
                            .withIdentifier(QualifiedIdentifier.from(new CqlParser().parseVersionedIdentifier(text)))
                            .withSource(text);
                }).collect(Collectors.toList());

        return new Plan().withLibraries(compileIdentifiedLibraries());
    }

    public Plan compile(List<QualifiedIdentifier> identifiedCql) {
        this.callScopedCqlSources = this.cqlSourceRepository.readById(identifiedCql);
        return new Plan().withLibraries(compileIdentifiedLibraries());
    }

    @Override
    public Plan compile(List<QualifiedIdentifier> identifiedCql, String... anonymousCql) {
        var compiledIdentifiedCql = this.cqlSourceRepository.readById(identifiedCql);
        this.callScopedCqlSources = new ArrayList<>(compiledIdentifiedCql);

        var compiledAnonymousCql = Stream.of(anonymousCql)
                .map(text -> {
                    return new CqlSource()
                            .withIdentifier(QualifiedIdentifier.from(new CqlParser().parseVersionedIdentifier(text)))
                            .withSource(text);
                }).collect(Collectors.toList());
        this.callScopedCqlSources.addAll(compiledAnonymousCql);

        return new Plan().withLibraries(compileIdentifiedLibraries());
    }

    private List<Library> compileIdentifiedLibraries() {
        var callSite = callScopedCqlSources.stream().map(cs -> execCompile(cs.getSource())).collect(Collectors.toList());
        var transitive = transitiveDependencies.stream().map(cs -> execCompile(cs.getSource())).collect(Collectors.toList());
        var allDependencies = new ArrayList<Library>(callSite);
        allDependencies.addAll(transitive);
        return allDependencies;
    }

    private Library execCompile(String cqlText) {
        this.transitiveDependencies = new ArrayList<CqlSource>();
        LibrarySourceProvider librarySourceProvider = new CqfLibrarySourceProvider(this.callScopedCqlSources, this.cqlSourceRepository, this.transitiveDependencies);
        UcumEssenceService ucumService = null;

        try {
            ucumService = new UcumEssenceService(UcumEssenceService.class.getResourceAsStream("/ucum-essence.xml"));
        } catch (UcumException e) {
            // Default to no service
        }

        var modelManager = new ModelManager();
        var libraryManager = new LibraryManager(modelManager);
        libraryManager.getCqlCompilerOptions().setOptions(CqlCompilerOptions.Options.EnableResultTypes);
        libraryManager.getLibrarySourceLoader().registerProvider(librarySourceProvider);

        var translator = CqlTranslator.fromText(cqlText, libraryManager);
        var elm = translator.toELM();

        // If there are any errors, throw them now. Prefer loud error reporting over quiet failures.
        var errorChecker = new CqlErrorChecker(elm);
        if (errorChecker.hasErrors()) {
            throw new RuntimeException(errorChecker.toPrettyString());
        }

        // the CQF evaluator uses binary searches which requires a sorted ExpressionDef
        // list. Perform that sort here so it's generally available.
        if (elm.getStatements() != null && !elm.getStatements().getDef().isEmpty())
            elm.getStatements().getDef().sort(Comparator.comparing(ExpressionDef::getName));

        return elm;
    }

    private static class CqfLibrarySourceProvider implements LibrarySourceProvider {

        protected List<CqlSource> callScopedCqlSources;
        protected List<CqlSource> transitiveCqlSources;
        protected CqlSourceRepository cqlSourceRepository;
    
        CqfLibrarySourceProvider(List<CqlSource> callScopedCqlSources, CqlSourceRepository cqlSourceRepository, ArrayList<CqlSource> transitiveCqlSources) {
            this.callScopedCqlSources = callScopedCqlSources;
            this.cqlSourceRepository = cqlSourceRepository;
            this.transitiveCqlSources = transitiveCqlSources;
        }
    
        @Override
        public InputStream getLibrarySource(VersionedIdentifier libraryIdentifier) {
            var qualifiedIdentifier = QualifiedIdentifier.from(libraryIdentifier);
            var lookup = callScopedCqlSources.stream().filter(cs -> cs.getIdentifier().equals(qualifiedIdentifier)).findFirst();
            // If the source wasn't specified at the "compile" call site, attempt to load it
            // from the repository. As a dependency, it becomes part of the Plan.
            if (lookup.isEmpty()) {
                var cs = this.cqlSourceRepository.readById(qualifiedIdentifier);
                this.transitiveCqlSources.add(cs);
                lookup = Optional.ofNullable(cs);
            }
    
            return new ByteArrayInputStream(lookup.get().getSource().getBytes());
        }
    }    
}