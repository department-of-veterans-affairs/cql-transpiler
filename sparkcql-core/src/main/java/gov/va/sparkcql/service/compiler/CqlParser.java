package gov.va.sparkcql.service.compiler;

import org.hl7.elm.r1.VersionedIdentifier;

public class CqlParser {
    public VersionedIdentifier parseVersionedIdentifier(String cqlText) {
        // Split out the lines of the CQL library and find the "library" statement.
        var lines = cqlText.split("\n");
        for (var line: lines) {
            if (line.trim().toLowerCase().startsWith("library")) {
                // Parse out the tokens of the library statement into a VersionedIdentifier.
                var tokens = line.split(" ");
                if (tokens.length == 4) {
                    return new VersionedIdentifier()
                        .withId(tokens[1])
                        .withVersion(tokens[3].replace("\'", "").trim());
                } else if (tokens.length == 2) {
                    return new VersionedIdentifier()
                        .withId(tokens[1].trim());
                } else {
                    throw new RuntimeException("Unexpected number of tokens in Library name - " + tokens.length);
                }
            }
        }

        // Must be an anonymous CQL library
        return new VersionedIdentifier()
            .withId("Anonymous-" + java.util.UUID.randomUUID().toString());
    }    
}