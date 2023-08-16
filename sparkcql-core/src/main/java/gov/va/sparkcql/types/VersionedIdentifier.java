package gov.va.sparkcql.types;

public class VersionedIdentifier {
    
    private String id = "";

    private String system = "";

    private String version = "";

    public VersionedIdentifier(String id) {
        this.id = id;
    }

    public VersionedIdentifier(String system, String id) {
        this.system = system;
        this.id = id;
    }

    public VersionedIdentifier(String system, String id, String version) {
        this.system = system;
        this.id = id;
        this.version = version;
    }

    public VersionedIdentifier(org.hl7.elm.r1.VersionedIdentifier versionedIdentifier) {
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
        var vid = (VersionedIdentifier)o;
        return this.system.equals(vid.system) && this.id.equals(vid.id) && this.version.equals(vid.system);
    }
}