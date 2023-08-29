package gov.va.sparkcql.pipeline.retriever.resolution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.inject.Inject;
import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.types.DataType;

public class TemplateResolutionStrategy implements TableResolutionStrategy {

    public static final String TEMPLATE_RESOLUTION_STRATEGY = "sparkcql.resolutionstrategy.template";
    protected EnvironmentConfiguration environmentConfiguration;

    protected Map<String, String> tokens;

    @Inject
    public TemplateResolutionStrategy(EnvironmentConfiguration environmentConfiguration) {
        this.environmentConfiguration = environmentConfiguration;
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
        var modelId = getWellKnownModelIdentifier(dataType);
        addToken("model", modelId.getId());
        addToken("version", modelId.getVersion());
        addToken("domain", dataType.getName());
        
        var template = this.environmentConfiguration.readSetting(TEMPLATE_RESOLUTION_STRATEGY, "${model}.${domain}");
        for (var tokenEntry : tokens.entrySet()) {
            template = template.replace(tokenEntry.getKey(), tokenEntry.getValue());
        }
        
        return template;
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