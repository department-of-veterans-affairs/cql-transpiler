package gov.va.sparkcql.repository.resolution;

import java.util.HashMap;
import java.util.Map;

import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.types.DataType;
import gov.va.sparkcql.types.VersionedIdentifier;

public class FormulaResolutionStrategy implements TableResolutionStrategy {

    protected SystemConfiguration systemConfiguration;

    protected Map<String, String> tokens;

    public FormulaResolutionStrategy(SystemConfiguration systemConfiguration) {
        this.systemConfiguration = systemConfiguration;
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
        
        var formula = this.systemConfiguration.read("sparkcql.resolutionstrategy.formula", "${model}.${domain}");
        for (var tokenEntry : tokens.entrySet()) {
            formula = formula.replace(tokenEntry.getKey(), tokenEntry.getValue());
        }
        
        return formula;
    }

    public VersionedIdentifier getWellKnownModelIdentifier(DataType dataType) {
        if (dataType.getNamespaceUri() == "http://hl7.org/fhir")
            return new VersionedIdentifier("fhir");
        
        if (dataType.getNamespaceUri().startsWith("urn:healthit-gov:qdm:")) {
            var tokens = dataType.getNamespaceUri().split(":");
            return new VersionedIdentifier("", "qdm", tokens[tokens.length - 1]);
        }

        return new VersionedIdentifier("", "default");
    }

    protected void addToken(String name, String value) {
        var qualifiedName = "${" + name + "}";
        tokens.put(qualifiedName, value);
        tokens.put(qualifiedName.toLowerCase(), value.toLowerCase());
        tokens.put(qualifiedName.toUpperCase(), value.toUpperCase());
    }
}