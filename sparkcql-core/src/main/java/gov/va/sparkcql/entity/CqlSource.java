package gov.va.sparkcql.entity;

import java.io.Serializable;

import org.hl7.elm.r1.VersionedIdentifier;

public final class CqlSource {

    private VersionedIdentifier identifier;
    private String source;

    public VersionedIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(VersionedIdentifier identifier) {
        this.identifier = identifier;
    }

    public CqlSource withIdentifier(VersionedIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public CqlSource withSource(String source) {
        this.source = source;
        return this;
    }
}
