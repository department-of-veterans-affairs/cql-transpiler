package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.LibraryCollection;
import org.cqframework.cql.cql2elm.model.CompiledLibrary;
import org.hl7.elm.r1.VersionedIdentifier;

import java.util.Map;
import java.util.stream.Collectors;

public class LibraryCacheAdapter {

    private final LibraryCollection libraryCollection;

    public LibraryCacheAdapter(LibraryCollection libraryCollection) {
        this.libraryCollection = libraryCollection;
    }

    public Map<VersionedIdentifier, CompiledLibrary> getVersionedIdentifierToCompiledLibraryMap() {
        return libraryCollection.stream().map(l -> {
            var compiledLibrary = new CompiledLibrary();
            compiledLibrary.setIdentifier(l.getIdentifier());
            compiledLibrary.setLibrary(l);
            return compiledLibrary;
        }).collect(Collectors.toMap(CompiledLibrary::getIdentifier, v -> v));
    }

    public LibraryCollection getLibraryCollection() {
        return libraryCollection;
    }
}
