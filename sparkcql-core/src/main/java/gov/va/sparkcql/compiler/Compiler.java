package gov.va.sparkcql.compiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.adapter.library.LibraryAdapter;
import gov.va.sparkcql.adapter.library.LibraryAdapterAggregator;

/**
 * Scopes searched when resolving library references (in order of precedence):
 * 1. Call scope
 * 2. Adapter scope
 */
public class Compiler implements LibrarySourceProvider {

    protected CompilerGateway compilerGateway = new CompilerGateway();
    protected LibraryAdapterAggregator libraryAdapters;
    protected Map<VersionedIdentifier, String> identifiedLibraries;

    public Compiler() {
        this.compilerGateway = new CompilerGateway();
        this.libraryAdapters = new LibraryAdapterAggregator(List.of());
    }

    public Compiler(List<LibraryAdapter> libraryAdapters) {
        this.compilerGateway = new CompilerGateway();
        this.libraryAdapters = new LibraryAdapterAggregator(libraryAdapters);
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
    public List<Library> compile(String... callScopedLibrarySources) {

        this.identifiedLibraries = Stream.of(callScopedLibrarySources)
            .collect(Collectors.toMap(compilerGateway::parseVersionedIdentifier, l -> l));

        return compileIdentifiedLibraries();
    }

    public List<Library> compile(List<VersionedIdentifier> callScopedLibraryIdentifiers) {

        this.identifiedLibraries = callScopedLibraryIdentifiers.stream()
            .collect(Collectors.toMap(i -> i, i -> this.libraryAdapters.getLibrarySource(i)));
        
        return compileIdentifiedLibraries();
    }

    protected List<Library> compileIdentifiedLibraries() {
        return identifiedLibraries.entrySet().stream().map(entry -> compilerGateway.compile(entry.getValue(), this)).toList();
    }

    @Override
    public InputStream getLibrarySource(org.hl7.elm.r1.VersionedIdentifier libraryIdentifier) {
        var isCached = identifiedLibraries.containsKey(libraryIdentifier);
        if (!isCached) {
            var source = this.libraryAdapters.getLibrarySource(libraryIdentifier);
            identifiedLibraries.put(libraryIdentifier, source);
        }

        return new ByteArrayInputStream(identifiedLibraries.get(libraryIdentifier).getBytes());
    }
}
