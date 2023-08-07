package gov.va.sparkcql.compiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.model.CqlSource;
import gov.va.sparkcql.repository.CqlSourceRepository;

/**
 * Scopes searched when resolving library references (in order of precedence):
 * 1. Call scope
 * 2. Adapter scope
 */
public class Compiler implements LibrarySourceProvider {

    protected CompilerGateway compilerGateway = new CompilerGateway();
    protected List<CqlSource> cqlSources;
    protected CqlSourceRepository cqlSourceRepository;

    public Compiler() {
        this.compilerGateway = new CompilerGateway();
    }

    public Compiler(CqlSourceRepository cqlSourceRepository) {
        this.compilerGateway = new CompilerGateway();
        this.cqlSourceRepository = cqlSourceRepository;
    }

    /*
     * Builds a set of call-scoped CQL libraries based on its CQL text alone. The
     * CQL scripts can reference each other through
     * [[https://cql.hl7.org/02-authorsguide.html#libraries
     * Include Declarations]] or reference adapter-scoped libraries set in the class
     * constructor. Call-scoped libraries can contain fragments are remain anonymous
     * (no [[https://cql.hl7.org/02-authorsguide.html#library Library Declaration
     * required]]).
     * 
     */
    public List<Library> compile(String... callScopedCqlSources) {
        this.cqlSources = Stream.of(callScopedCqlSources)
            .map(cqlText -> {
                return new CqlSource()
                    .withIdentifier(compilerGateway.parseVersionedIdentifier(cqlText))
                    .withSource(cqlText);
            }).toList();

        return compileIdentifiedLibraries();
    }

    public List<Library> compile(List<VersionedIdentifier> callScopedLibraryIdentifiers) {
        this.cqlSources = this.cqlSourceRepository.findMany(callScopedLibraryIdentifiers);
        return compileIdentifiedLibraries();
    }

    protected List<Library> compileIdentifiedLibraries() {
        return cqlSources.stream().map(cs -> compilerGateway.compile(cs.getSource(), this)).toList();
    }

    @Override
    public InputStream getLibrarySource(org.hl7.elm.r1.VersionedIdentifier libraryIdentifier) {
        var lookup = cqlSources.stream().filter(cs -> cs.getIdentifier().equals(libraryIdentifier)).findFirst();
        if (lookup.isEmpty()) {
            var cs = this.cqlSourceRepository.findOne(libraryIdentifier);
            this.cqlSources.add(cs);
            lookup = Optional.of(cs);
        }

        return new ByteArrayInputStream(lookup.get().getSource().getBytes());
    }
}
