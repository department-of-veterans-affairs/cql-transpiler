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

/**
 * Retrieves text from inside the original CQL files or string provided for transpilation.
 */
public class CQLFileContentRetriever {

    private final LibrarySourceProvider librarySourceProvider;
    private final String anonymousCQL;

    /**
     * @param librarySourceProvider {@link LibrarySourceProvider} with all necessary CQL library files.
     * @param anonymousCQL If CQL is provided as text to the transpiler, it should be specified here.
     */
    public CQLFileContentRetriever(LibrarySourceProvider librarySourceProvider, String anonymousCQL) {
        this.librarySourceProvider = librarySourceProvider;
        this.anonymousCQL = anonymousCQL;
    }

    /**
     * @param versionedIdentifier Identifier for the CQL library to look for text in.
     * @param locator Specified the location of the text in the original CQL library file.
     * @return If {@code versionedIdentifier} is valid, returns the lines of text specified in the original CQL library file. If it is not, returns the specified lines of text from {@code anonymousCQL} provided to the constructor. Does NOT concatenate lines to begin at the start or end characters specified by {@code locator}.
     * @throws IOException
     */
    public List<String> getLinesOfTextFromLibrary(VersionedIdentifier versionedIdentifier, Locator locator) throws IOException {
        InputStream libraryContents = librarySourceProvider.getLibrarySource(versionedIdentifier);
        // if libraryContents are null, the versionedIdentifier did not match any of the libraries in the librarySourceProvider. Retrieve lines from the anonymousCQL instead.
        libraryContents = libraryContents == null ? new ByteArrayInputStream(anonymousCQL.getBytes()) : libraryContents;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(libraryContents))) {
            return reader.lines().skip(locator.getStartLine() - 1).limit(locator.getEndLine() - locator.getStartLine() + 1).collect(Collectors.toList());
        }
    }
}
