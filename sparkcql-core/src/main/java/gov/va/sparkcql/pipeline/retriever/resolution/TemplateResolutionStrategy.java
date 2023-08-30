package gov.va.sparkcql.pipeline.retriever.resolution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.types.DataType;

public class TemplateResolutionStrategy implements TableResolutionStrategy {

    private String template;
    protected Map<String, String> tokens;

    public TemplateResolutionStrategy(String template) {
        this.template = template;
        this.tokens = new HashMap<String, String>();
    }

    /**
     * Namespace Examples:
     * urn:hl7-org:elm-types:r1
     * urn:healthit-gov:qdm:v5_6
     * 
     */
    @Override
    public String resolveTableBinding(DataType dataType) {
        var tableName = template;
        var modelId = getWellKnownModelIdentifier(dataType);
        addToken("model", modelId.getId());
        addToken("version", modelId.getVersion());
        addToken("domain", dataType.getName());
        
        for (var tokenEntry : tokens.entrySet()) {
            tableName = tableName.replace(tokenEntry.getKey(), tokenEntry.getValue());
        }
        
        return tableName;
    }

    public VersionedIdentifier getWellKnownModelIdentifier(DataType dataType) {
        if (Objects.equals(dataType.getNamespaceUri(), "http://hl7.org/fhir"))
            return new VersionedIdentifier().withId("fhir");
        
        if (dataType.getNamespaceUri().startsWith("urn:healthit-gov:qdm:")) {
            var tokens = dataType.getNamespaceUri().split(":");
            return new VersionedIdentifier().withId("qdm").withVersion(tokens[tokens.length - 1]);
        }

        return new VersionedIdentifier().withId("default");
    }

    protected void addToken(String name, String value) {
        var qualifiedName = "${" + name + "}";
        if (value == null) value = "";
        tokens.put(qualifiedName, value);
        tokens.put(qualifiedName.toLowerCase(), value.toLowerCase());
        tokens.put(qualifiedName.toUpperCase(), value.toUpperCase());
    }
}