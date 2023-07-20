package gov.va.sparkcql.adapter.library;

import java.util.Map;
import java.util.stream.Collectors;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.compiler.CompilerGateway;

import gov.va.sparkcql.common.Directory;

public class FileLibraryAdapter implements LibraryAdapter {

    protected Map<VersionedIdentifier, String> contents;

    public FileLibraryAdapter(String path) {
        if (path == null || path == "") {
            contents = Map.of();
        } else {
            var compilerGateway = new CompilerGateway();
            contents = Directory
                .find(path, ".cql")
                .map(Directory::readString) 
                .collect(Collectors.toMap(compilerGateway::parseVersionedIdentifier, c -> c));
        }
    }

    @Override
    public String getLibrarySource(VersionedIdentifier identifier) {
        if (!contents.containsKey(identifier)) {
            throw new RuntimeException("Library " + identifier.toString() + " not found.");
        }
        return contents.get(identifier);
    }

    @Override
    public Boolean containsLibrary(VersionedIdentifier identifier) {
        return contents.containsKey(identifier);
    }
}