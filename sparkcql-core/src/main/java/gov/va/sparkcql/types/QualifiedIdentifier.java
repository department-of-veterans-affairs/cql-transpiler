package gov.va.sparkcql.types;

import java.io.Serializable;
import java.util.Objects;

public class QualifiedIdentifier implements Serializable {
    
    private String id = "";

    private String system = "";

    private String version = "";

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
        this.id = versionedIdentifier.getId();
        this.system = versionedIdentifier.getSystem();
        this.version = versionedIdentifier.getVersion();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return this.system.equals("") ? this.id : "{" + this.system + "}" + this.id + (this.version.equals("") ? "" : ":" + this.version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        var vid = (QualifiedIdentifier)o;
        return this.system.equals(vid.system) && this.id.equals(vid.id) && this.version.equals(vid.system);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }
}