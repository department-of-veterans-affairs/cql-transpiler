package gov.va.transpiler.jinja.printing;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.hl7.elm.r1.VersionedIdentifier;

public class CQLFileContentRetriever {

    private final LibrarySourceProvider librarySourceProvider;
    private final String anonymousCQL;

    public CQLFileContentRetriever(LibrarySourceProvider librarySourceProvider, String anonymousCQL) {
        this.librarySourceProvider = librarySourceProvider;
        this.anonymousCQL = anonymousCQL;
    }

    public List<String> getTextFromLibrary(VersionedIdentifier versionedIdentifier, Locator locator) throws IOException {
        InputStream libraryContents = librarySourceProvider.getLibrarySource(versionedIdentifier);
        libraryContents = libraryContents == null ? new ByteArrayInputStream(anonymousCQL.getBytes()) : libraryContents;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(libraryContents))) {
            return reader.lines().skip(locator.getStartLine() - 1).limit(locator.getEndLine() - locator.getStartLine() + 1).collect(Collectors.toList());
        }
    }
}
