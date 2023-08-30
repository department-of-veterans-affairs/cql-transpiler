package gov.va.sparkcql.domain;

import gov.va.sparkcql.types.QualifiedIdentifier;

import java.io.Serializable;

public final class CqlSource implements Serializable {

    private QualifiedIdentifier identifier;
    private String source;

    public QualifiedIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(QualifiedIdentifier identifier) {
        this.identifier = identifier;
    }

    public CqlSource withIdentifier(QualifiedIdentifier identifier) {
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
