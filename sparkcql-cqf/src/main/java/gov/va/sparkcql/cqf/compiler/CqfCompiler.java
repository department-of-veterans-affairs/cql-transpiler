package gov.va.sparkcql.cqf.compiler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gov.va.sparkcql.cqf.domain.CqlSource;
import org.cqframework.cql.cql2elm.*;
import org.fhir.ucum.UcumEssenceService;
import org.fhir.ucum.UcumException;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

public class CqfCompiler {

    protected List<CqlSource> callScopedCqlSources;
    protected LibrarySourceProvider librarySourceProvider;

    // An included CQL source not specified at the "compile" call site. Will be included as
    // part of the plan.
    protected ArrayList<CqlSource> transitiveDependencies;

    public CqfCompiler(LibrarySourceProvider librarySourceProvider) {
        this.librarySourceProvider = librarySourceProvider;
    }

    public List<Library> compile(String... anonymousCql) {
        this.callScopedCqlSources = Stream.of(anonymousCql)
                .map(text -> {
                    return new CqlSource()
                            .withIdentifier(CqlParser.parseVersionedIdentifier(text))
                            .withSource(text);
                }).collect(Collectors.toList());

        return compileIdentifiedLibraries();
    }

    public List<Library> compile(List<VersionedIdentifier> cqlIdentifiers) {
        this.callScopedCqlSources = cqlIdentifiers
                .stream()
                .map(identifier -> CqfCompiler.fromLibrary(librarySourceProvider, identifier))
                .collect(Collectors.toList());
        return compileIdentifiedLibraries();
    }

    public List<Library> compile(List<VersionedIdentifier> cqlIdentifiers, String... anonymousCql) {
        this.callScopedCqlSources = cqlIdentifiers
                .stream()
                .map(identifier -> CqfCompiler.fromLibrary(librarySourceProvider, identifier))
                .collect(Collectors.toList());

        var compiledAnonymousCql = Stream.of(anonymousCql)
                .map(text -> {
                    return new CqlSource()
                            .withIdentifier(CqlParser.parseVersionedIdentifier(text))
                            .withSource(text);
                }).collect(Collectors.toList());
        this.callScopedCqlSources.addAll(compiledAnonymousCql);

        return compileIdentifiedLibraries();
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
        LibrarySourceProvider librarySourceProvider = new CallScopedLibrarySourceProvider(this.callScopedCqlSources, this.librarySourceProvider, this.transitiveDependencies);
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

    private static CqlSource fromLibrary(LibrarySourceProvider librarySourceProvider, VersionedIdentifier identifier) {
        try {
            var sourceInputStream = librarySourceProvider.getLibrarySource(identifier);
            var source = new String(sourceInputStream.readAllBytes(), StandardCharsets.UTF_8);
            return new CqlSource()
                    .withIdentifier(identifier)
                    .withSource(source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class CallScopedLibrarySourceProvider implements LibrarySourceProvider {

        protected List<CqlSource> callScopedCqlSources;
        protected List<CqlSource> transitiveCqlSources;
        protected LibrarySourceProvider enclosedLibrarySourceProvider;

        CallScopedLibrarySourceProvider(List<CqlSource> callScopedCqlSources, LibrarySourceProvider enclosedLibrarySourceProvider, ArrayList<CqlSource> transitiveCqlSources) {
            this.callScopedCqlSources = callScopedCqlSources;
            this.enclosedLibrarySourceProvider = enclosedLibrarySourceProvider;
            this.transitiveCqlSources = transitiveCqlSources;
        }

        @Override
        public InputStream getLibrarySource(VersionedIdentifier libraryIdentifier) {
            var lookup = callScopedCqlSources.stream().filter(cs -> cs.getIdentifier().equals(libraryIdentifier)).findFirst();
            // If the source wasn't specified at the "compile" call site, attempt to load it
            // from the repository. As a dependency, it becomes part of the output.
            if (lookup.isEmpty()) {
                var cs = CqfCompiler.fromLibrary(
                        this.enclosedLibrarySourceProvider,
                        libraryIdentifier);
                this.transitiveCqlSources.add(cs);
                return new ByteArrayInputStream(cs.getSource().getBytes());
            } else {
                return new ByteArrayInputStream(lookup.get().getSource().getBytes());
            }
        }
    }
}