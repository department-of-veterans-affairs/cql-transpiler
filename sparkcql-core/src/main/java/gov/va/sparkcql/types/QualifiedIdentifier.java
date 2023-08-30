package gov.va.sparkcql.types;

import org.hl7.elm.r1.VersionedIdentifier;

import java.io.Serializable;
import java.util.Objects;

public class QualifiedIdentifier implements Serializable {
    
    private String id = "";

    private String system = "";

    private String version = "";

    public QualifiedIdentifier() {
    }

    public QualifiedIdentifier(String id) {
        this.id = id;
    }

    public QualifiedIdentifier(String system, String id) {
        this.system = system;
        this.id = id;
    }

    public QualifiedIdentifier(String system, String id, String version) {
        this.system = system;
        this.id = id;
        this.version = version;
    }

    public QualifiedIdentifier(org.hl7.elm.r1.VersionedIdentifier versionedIdentifier) {
        this.id = ifNull(versionedIdentifier.getId(), "");
        this.system = ifNull(versionedIdentifier.getSystem(), "");
        this.version = ifNull(versionedIdentifier.getVersion(), "");
    }

    private <T> T ifNull(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QualifiedIdentifier withId(String id) {
        this.id = id;
        return this;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public QualifiedIdentifier withSystem(String system) {
        this.system = system;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public QualifiedIdentifier withVersion(String version) {
        this.version = version;
        return this;
    }

    public VersionedIdentifier toVersionedIdentifier() {
        return new VersionedIdentifier()
                .withSystem(getSystem())
                .withId(getId())
                .withVersion(getVersion());
    }

    public static QualifiedIdentifier from(VersionedIdentifier versionedIdentifier) {
        return new QualifiedIdentifier(versionedIdentifier);
    }

    @Override
    public String toString() {
        var systemText = system == null || system.isEmpty() ? "" : "{" + this.system + "}";
        var idText = this.id;
        var versionText = version == null || version.isEmpty() ? "" : ":" + this.version;
        return systemText + idText + versionText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;

        if (getClass() == o.getClass() || o.getClass() == VersionedIdentifier.class) {
            var c = o.getClass() == VersionedIdentifier.class ?
                    QualifiedIdentifier.from((VersionedIdentifier) o) : (QualifiedIdentifier) o;
            return this.system.equals(c.system) && this.id.equals(c.id) && this.version.equals(c.version);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }
}