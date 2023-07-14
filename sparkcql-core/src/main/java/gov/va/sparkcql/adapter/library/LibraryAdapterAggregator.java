package gov.va.sparkcql.adapter.library;

import java.util.List;

import org.hl7.elm.r1.VersionedIdentifier;

public class LibraryAdapterAggregator implements LibraryAdapter {
    
    protected List<LibraryAdapter> libraryAdapters;

    public LibraryAdapterAggregator(List<LibraryAdapter> libraryAdapters) {
        this.libraryAdapters = libraryAdapters;
    }

    @Override
    public String getLibrarySource(VersionedIdentifier identifier) {
        var resolvedAdapter = libraryAdapters.stream().filter(a -> a.containsLibrary(identifier));
        if (resolvedAdapter.count() > 0) {
            return resolvedAdapter.findFirst().get().getLibrarySource(identifier);
        } else {
            throw new RuntimeException("Library " + identifier.toString() + " not found.");
        }
    }

    @Override
    public Boolean containsLibrary(VersionedIdentifier identifier) {
        return libraryAdapters.stream().filter(a -> a.containsLibrary(identifier)).count() > 0;
    }
}